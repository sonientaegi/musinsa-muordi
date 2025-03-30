package com.musinsa.muordi.platform.domain.brand;

import com.musinsa.muordi.platform.domain.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandTest {
    @Autowired
    BrandRepository repository;

    @Autowired
    ProductRepository productRepository;

    private Brand testCaseWithoutProducts;
    private Brand testCaseWithProducts;

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        this.testCaseWithoutProducts = this.repository.save(new Brand(null, "BRAND NO PRODUCT", null));
        this.testCaseWithProducts = this.repository.save(new Brand(null, "BRAND WITH PRODUCT", null));
    }

    @Test
    void testCloneWithoutProducts() {
        Brand expected = this.testCaseWithoutProducts;
        Brand actual = (Brand) expected.clone();
        assertEquals(expected, actual);
    }
}