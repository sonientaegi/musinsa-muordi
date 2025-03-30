package com.musinsa.muordi.platform.domain.product;

import com.musinsa.muordi.platform.domain.DomainService;
import com.musinsa.muordi.platform.domain.brand.BrandRepositoryTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("test-product")
@SpringBootTest
class ProductRepositoryJpaTest {
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

//    @Autowired
//    private ProductRepository repository;

    // 테스트케이스
    private Map<Integer, Product> testCases = new HashMap<>();

//    @BeforeEach
//    void setUp() {
//
//    }
//
    @Test
    @Transactional
    void testSave() {
        Product expected = Product.builder().price(10000).build();
        this.repository.save(expected, 1);
//        Product actual = this.repository.save(expected, 1);
//        assertNotNull(actual);
//        assertNotNull(actual.getId());
//        assertNotNull(actual.getBrand());
//        assertEquals(expected.getPrice(), actual.getPrice());
    }
//
//
//    @Test
//    void findByBrand() {
//
//    }
//
//    @Test
//    void testFindByBrand() {
//    }
//
//    @Test
//    void findByBrandId() {
//    }
//
//    @Test
//    void findByIdWithLock() {
//    }
//
//    @Test
//    void updateById() {
//    }
}