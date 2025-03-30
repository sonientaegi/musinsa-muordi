package com.musinsa.muordi.platform.admin.repository;

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

@ActiveProfiles("test-product")
@SpringBootTest
public class ProductRepositoryTest {
    /*
    주의.
    JPA에서 FK 참조를 할때 EAGER 모드로 설정해도 Test 환경에서는 LAZY로 동작한다. 반드시 Transaction 설정하고 TC 구현할것.
     */

    /**
     * DTO 테스트를 지원하기위해 제공하는 샘플 데이터 입니다.
     * @return
     */
    public static Product sample() {
        return new Product(1l, BrandRepositoryTest.sample(), 50000);
    }

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository repository;

    // 테스트케이스
    private Map<Long, Product> testCases = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 각 브랜드별로 두개씩 상품을 생성해준다.
        Random rand = new Random();
        this.brandRepository.findAll().forEach(brand -> {
            IntStream.range(0, 2).forEach(i -> {
                Product product = Product.builder()
                        .brand(brand)
                        .price(rand.nextInt(1000, 100000))
                        .build();
                Product testCase = this.repository.save(product);
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

    // 상품 전체 조회.
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

    // 상품 아이디로 단건 조회.
    @Test
    @DisplayName("상품 단건 조회")
    @Transactional
    void testFindById() {
        Product expected = this.randProduct();
        Product actual = this.repository.findById(expected.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    // 무작위로 고른 브랜드명을 가지는 모든 상품을 조회.
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

    // 무작위로 고른 브랜드 식별자를을 가지는 모든 상품을 조회.
    @Test
    @DisplayName("브랜드 식별자로 상품 조회")
    @Transactional
    void testFndByBrandId() {
        int brandId = this.randProduct().getBrand().getId();

        // 전체 테스트케이스에서 브랜드 식별자가 일치하는 대상을 추린다.
        List<Product> expecteds = this.testCases.values().stream()
                .filter(p -> p.getBrand().getId().equals(brandId))
                .sorted(Comparator.comparingLong(Product::getId))
                .toList();

        // 브랜드 식별자 조회 검증.
        List<Product> actuals = this.repository.findByBrandId(brandId);
        assertNotNull(actuals);
        actuals.sort(Comparator.comparingLong(Product::getId));
        assertEquals(expecteds, actuals);
    }

    // 상품 생성.
    @Test
    @DisplayName("상품 생성")
    @Transactional
    void testSave() {
        Product expected = Product.builder()
                .brand(this.randProduct().getBrand())
                .price(new Random().nextInt(1000, 100000))
                .build();
        Product actual = this.repository.save(expected);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getBrand());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    // 상품 수정.
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
        Product expected = Product.builder()
                .brand(expectedBrand)
                .price(new Random().nextInt(1000, 100000))
                .build();
        Product actual = this.repository.updateById(testCase.getId(), expected).orElse(null);
        assertNotNull(actual);
        assertEquals(expected.getBrand(), actual.getBrand());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    // 상품 삭제
    @Test
    @DisplayName("상품 삭제")
    @Transactional
    void testDelete() {
        Product testCase = this.randProduct();
        this.repository.deleteById(testCase.getId());
        Product actual = this.repository.findById(testCase.getId()).orElse(null);
        assertNull(actual);
    }

    // 존재하지 않는 상품 수정
    // 예외 발생 : ObjectOptimisticLockingFailureException
    @Test
    @DisplayName("예외 발생 : 존재하지 않는 상품 수정")
    @Transactional
    void testUpdateNotExists() {
        Product expected = this.randProduct();
        expected.setPrice(Integer.MAX_VALUE);

        // TODO 존재하지 않는 상품 수정 시 exception / null 어떻게 처리할지 고민.
        // 예외 발생 : ObjectOptimisticLockingFailureException
        Optional<Product> actual = this.repository.updateById(Integer.MIN_VALUE, expected);
    }


//    @AfterEach
//    void tearDown() {
//    }
}