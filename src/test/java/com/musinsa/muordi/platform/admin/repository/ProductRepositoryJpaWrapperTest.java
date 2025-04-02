package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.exception.RepositoryNotExistException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/*
PRODUCT 의 JpaRepository 테스트
 */
@ActiveProfiles("test-empty")
@SpringBootTest
public class ProductRepositoryJpaWrapperTest {
    /*
    주의.
    JPA에서 FK 참조를 할때 EAGER 모드로 설정해도 Test 환경에서는 LAZY로 동작한다. 반드시 Transaction 설정하고 TC 구현할것.
     */
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepositoryJpaWrapper repository;

    // 테스트케이스
    private final Map<Long, Product> testCases = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        List.of(
                new Brand(null, "BRAND 3", null),
                new Brand(null, "BRAND 4", null),
                new Brand(null, "BRAND 2", null),
                new Brand(null, "BRAND 1", null),
                new Brand(null, "BRAND 4", null)
        ).forEach(brand -> {
            this.brandRepository.create(brand);
        });

        // 각 브랜드별로 두개씩 상품을 생성해준다.
        Random rand = new Random();
        this.brandRepository.findAll().forEach(brand -> {
            IntStream.range(0, 2).forEach(i -> {
                Product product = new Product();
                product.setBrand(brand);
                product.setPrice(rand.nextInt(1000, 100000));
//                Product product = Product.builder()
//                        .brand(brand)
//                        .price(rand.nextInt(1000, 100000))
//                        .build();
                Product testCase = this.repository.create(product);
                this.testCases.put(testCase.getId(), testCase);
            });
        });
    }

    /**
     * 무작위로 한개의 상품을 선택한다.
     * @return
     */
    private Product randProduct() {
        List<Product> products = this.testCases.values().stream().toList();
        return products.get(new Random().nextInt(products.size())).clone();
    }

    @Test
    @DisplayName("상품 전체 조회")
    @Transactional
    void testFindAll() {
        this.repository.findAll().stream().forEach(expected -> {
            Product actual = this.repository.findById(expected.getId()).orElse(null);
            assertNotNull(actual);
            assertEquals(expected, actual);
        });
    }

    @Test
    @DisplayName("상품 ID 조회")
    @Transactional
    void testFindById() {
        Product expected = this.randProduct();
        Product actual = this.repository.findById(expected.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("브랜드 이름으로 상품 조회")
    @Transactional
    void testFndByBrandName() {
        String brandName = this.randProduct().getBrand().getName();

        // 전체 테스트케이스에서 브랜드명이 일치하는 대상을 추린다.
        List<Product> expecteds = this.testCases.values().stream()
                .filter(p -> p.getBrand().getName().equals(brandName))
                .sorted(Comparator.comparingLong(Product::getId))
                .toList();

        // 브랜드명 조회 검증.
        List<Product> actuals = this.repository.findByBrandName(brandName);
        assertNotNull(actuals);
        actuals.sort(Comparator.comparingLong(Product::getId));
        assertEquals(expecteds, actuals);
    }

    @Test
    @DisplayName("브랜드 ID로 상품 조회")
    @Transactional
    void testFndByBrandId() {
        int brandId = this.randProduct().getBrand().getId();

        // 전체 테스트케이스에서 브랜드 ID가 일치하는 대상을 추린다.
        List<Product> expecteds = this.testCases.values().stream()
                .filter(p -> p.getBrand().getId().equals(brandId))
                .sorted(Comparator.comparingLong(Product::getId))
                .toList();

        // 브랜드 ID 조회 검증.
        List<Product> actuals = this.repository.findByBrandId(brandId);
        assertNotNull(actuals);
        actuals.sort(Comparator.comparingLong(Product::getId));
        assertEquals(expecteds, actuals);
    }

    @Test
    @DisplayName("상품 생성")
    @Transactional
    void testSave() {
//        Product expected = Product.builder()
//                .brand(this.randProduct().getBrand())
//                .price(new Random().nextInt(1000, 100000))
//                .build();
        Product expected = this.randProduct();
        expected.setBrand(this.randProduct().getBrand());
        expected.setPrice(new Random().nextInt(1000, 100000));

        Product actual = this.repository.create(expected);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getBrand());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    @DisplayName("상품 수정")
    @Transactional
    void testUpdate() {
        Product testCase = this.randProduct();
        Brand expectedBrand = this.randProduct().getBrand();
        while (expectedBrand.getId() == testCase.getBrand().getId()) {
            expectedBrand = this.randProduct().getBrand();
        }

        // 브랜드와 금액을 동시에 변경한다.
//        Product expected = Product.builder()
//                .brand(expectedBrand)
//                .price(new Random().nextInt(1000, 100000))
//                .build();
        Product expected = new Product();
        expected.setBrand(expectedBrand);
        expected.setPrice(new Random().nextInt(1000, 100000));

        Product actual = this.repository.update(testCase.getId(), expected);
        assertNotNull(actual);
        assertEquals(expected.getBrand(), actual.getBrand());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    @DisplayName("상품 삭제")
    @Transactional
    void testDelete() {
        Product testCase = this.randProduct();
        this.repository.delete(testCase.getId());
        Product actual = this.repository.findById(testCase.getId()).orElse(null);
        assertNull(actual);
    }

    @Test
    @DisplayName("상품 수정 : 존재하지 않는 상품")
    @Transactional
    void testUpdateNotExists() {
        Product expected = this.randProduct();
        expected.setPrice(Integer.MAX_VALUE);

        assertThrows(RepositoryNotExistException.class, () -> this.repository.update(Integer.MIN_VALUE, expected));
    }
}