package com.musinsa.muordi.platform.domain.product;

import com.musinsa.muordi.platform.domain.DomainService;
import com.musinsa.muordi.platform.domain.brand.BrandRepositoryTest;
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

    // 서비스 객체는 카테고리, 브랜드를 다룰 시에만 사용.
    @Autowired
    private DomainService service;

    @Autowired
    private ProductRepository repository;

    // 테스트케이스
    private Map<Long, Product> testCases = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 각 브랜드별로 두개씩 상품을 생성해준다.
        Random rand = new Random();
        this.service.getBrands().forEach(brandDto -> {
            IntStream.range(0, 2).forEach(i -> {
                Product product = Product.builder().price(rand.nextInt(1000, 100000)).build();
                Product testCase = this.repository.save(product, brandDto.getId());
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
        return products.get(new Random().nextInt(products.size()));
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
    @DisplayName("상품 브랜드 조회")
    @Transactional
    void testFndByBrandName() {
        String expectedName = this.randProduct().getBrand().getName();

        // 전체 테스트케이스에서 브랜드명이 일치하는 대상을 추린다.
        List<Product> expecteds = this.testCases.values().stream()
                .filter(p -> p.getBrand().getName().equals(expectedName))
                .sorted(Comparator.comparingLong(Product::getId))
                .toList();

        // 브랜드명 조회 검증.
        List<Product> actuals = this.repository.findByBrandName(expectedName);
        assertNotNull(actuals);
        actuals.sort(Comparator.comparingLong(Product::getId));
        assertEquals(expecteds, actuals);
    }

    // 상품 저장.
    @Test
    @Transactional
    void testSave() {
        Product expected = Product.builder().price(new Random().nextInt(1000, 100000)).build();
        this.repository.save(expected, 1);
        Product actual = this.repository.save(expected, 1);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getBrand());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    // 상품 수정
    @Test
    @Transactional
    void testUpdate() {
        Product testCase = this.randProduct();
        Product expected = Product.builder().brand(testCase.getBrand()).price(new Random().nextInt(1000, 100000)).build();
        Product actual = this.repository.updateById(testCase.getId(), expected).orElse(null);
        assertNotNull(actual);
        assertEquals(expected.getBrand(), actual.getBrand());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    // 상품 삭제
    @Test
    @Transactional
    void testDelete() {
        Product testCase = this.randProduct();
        this.repository.deleteById(testCase.getId());
        Product actual = this.repository.findById(testCase.getId()).orElse(null);
        assertNull(actual);
    }

//
////    @AfterEach
////    void tearDown() {
////    }
//
//    // 신규 상품 생성
//    // 상품 식별자를 제외한 모든값은 그대로 유지.
//    @Test
//    @Transactional
//    void save() {
//        ProductDTO expected = new ProductDTO(null, this.brandDTOs[0].getId(), this.brandDTOs[0].getName(), 5000);
//        ProductDTO actual = this.productRepositoryImpl.save(expected);
//        assertNotNull(actual);
//        assertNotNull(actual.getId());
//        assertTrue(expected.getBrandId().equals(actual.getBrandId()) &&
//            expected.getBrandName().equals(actual.getBrandName()) &&
//            expected.getPrice().equals(actual.getPrice())
//        );
//    }
//
//    // 신규 상품 다건 생성
//    // 상품 식별자를 제외한 모든값은 그대로 유지.
//    @Test
//    @Transactional
//    void saveAll() {
//        List<ProductDTO> expecteds = List.of(
//            new ProductDTO(null, this.brandDTOs[0].getId(), this.brandDTOs[0].getName(), 5000),
//            new ProductDTO(null, this.brandDTOs[1].getId(), this.brandDTOs[1].getName(), 9100),
//            new ProductDTO(null, this.brandDTOs[0].getId(), this.brandDTOs[0].getName(), 1220),
//            new ProductDTO(null, this.brandDTOs[1].getId(), this.brandDTOs[1].getName(), 3900)
//        );
//        List<ProductDTO> actuals = this.productRepositoryImpl.saveAll(expecteds);
//        assertNotNull(actuals);
//        for (int i=0; i < expecteds.size(); i++) {
//            ProductDTO actual = actuals.get(i);
//            ProductDTO expected = expecteds.get(i);
//
//            assertNotNull(actual.getId());
//            assertTrue(expected.getBrandId().equals(actual.getBrandId()) &&
//                expected.getBrandName().equals(actual.getBrandName()) &&
//                expected.getPrice().equals(actual.getPrice()));
//        }
//    }
//
//    // 상품 수정
//    // 상품 가격만 수정이 가능하다.
//    @Test
//    @Transactional
//    void update() {
//        ProductDTO original = this.testCases.values().iterator().next();
//        ProductDTO expected = (ProductDTO) original.clone();
//        expected.setPrice(original.getPrice() * 2);
//        ProductDTO actual = this.productRepositoryImpl.save(expected);
//
//        assertNotNull(actual);
//        assertEquals(expected.getPrice(), actual.getPrice());
//        assertNotEquals(original.getPrice(), actual.getPrice());
//        assertTrue(expected.getId().equals(actual.getId()) &&
//                expected.getBrandId().equals(actual.getBrandId()) &&
//                expected.getBrandName().equals(actual.getBrandName()));
//    }
//
//    // 상품 다건 수정
//    // 상품 가격만 수정이 가능하다.
//    @Test
//    @Transactional
//    void updateAll() {
//        // 테스트 케이스 사본 생성 후 금액 변경
//        List<ProductDTO> originals = this.testCases.values().stream().toList();
//        List<ProductDTO> expecteds = new ArrayList<>();
//        originals.forEach(originalDTO -> {
//            ProductDTO expected = (ProductDTO) originalDTO.clone();
//            expected.setPrice(originalDTO.getPrice() * 2);
//            expecteds.add(expected);
//        });
//
//        // 상품DB 수정 후 검증. 금액을 제외한 나머지 필드는 동일해야 함.
//        List<ProductDTO> actuals = this.productRepositoryImpl.saveAll(expecteds);
//        assertNotNull(actuals);
//        assertEquals(expecteds.size(), actuals.size());
//        for (int i=0; i < expecteds.size(); i++) {
//            ProductDTO original = originals.get(i);
//            ProductDTO expected = expecteds.get(i);
//            ProductDTO actual = actuals.get(i);
//            assertNotEquals(original.getPrice(), actual.getPrice());
//            assertEquals(expected.getPrice(), actual.getPrice());
//            assertTrue(expected.getBrandId().equals(actual.getBrandId()) &&
//                    expected.getBrandName().equals(actual.getBrandName()) &&
//                    expected.getPrice().equals(actual.getPrice()));
//        }
//    }
//
//    // 존재하지 않는 상품 수정
//    // 예외 발생 : ObjectOptimisticLockingFailureException
//    @Test
//    @Transactional
//    void updateNotExists() {
//        ProductDTO expected = (ProductDTO) this.testCases.values().iterator().next().clone();
//        expected.setId(Long.MAX_VALUE);
//        expected.setPrice(expected.getPrice() * 2);
//        assertThrows(ObjectOptimisticLockingFailureException.class, ()->this.productRepositoryImpl.save(expected));
//    }
//
//    // 상품 삭제
//    @Test
//    @Transactional
//    void delete() {
//        List<BrandDTO> originalBrands = this.brandRepository.all();
//        List<ProductDTO> originalProducts = this.productRepositoryImpl.findAll();
//        // this.productRepository.delete(this.testCases.values().iterator().next().getId());
//        this.brandRepository.deleteById(originalBrands.get(0).getId());
//        List<BrandDTO> actualBrands = this.brandRepository.all();
//        List<ProductDTO> actualProducts = this.productRepositoryImpl.findAll();
//    }
//
//    // 상품 브랜드 수정
//    // 어떻게 되려나.
//    @Test
//    @Transactional
//    void updateBrand() {
//        // 브랜드0 상품을 조회한 뒤 브랜드1로 변경하여 저장한다.
//        BrandDTO originalBrand = this.brandDTOs[0];
//        BrandDTO expectedBrand = this.brandDTOs[1];
//        ProductDTO expected = this.productRepositoryImpl.findByBrand(originalBrand.getId()).get(0);
//        expected.setBrandId(expectedBrand.getId());
//        expected.setBrandName(expectedBrand.getName());
//        ProductDTO actual = this.productRepositoryImpl.save(expected);
//
//        List<BrandDTO> actualBrands = this.brandRepository.all();
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    // 브랜드 삭제
//    // 브랜드 삭제 시 연관 상품이 모두 삭제된다.
//    @Test
//    @Transactional
//    void deleteBrand() {
//        List<ProductDTO> originals = this.productRepositoryImpl.findAll();
//
//        // 브랜드 삭제
//        this.brandRepository.deleteById(this.brandDTOs[0].getId());
//        List<BrandDTO> actualBrands = this.brandRepository.all();
//
//        List<ProductDTO> actuals = this.productRepositoryImpl.findAll();
//
//        // 결과 비교
//        assertNotNull(actuals);
//    }
}