package com.musinsa.muordi.platform.domain;

import com.musinsa.muordi.platform.domain.brand.Brand;
import com.musinsa.muordi.platform.domain.brand.BrandRepository;
import com.musinsa.muordi.platform.domain.category.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class DomainServiceTest {
    @Autowired
    private DomainService service;

    // 검증을 위해 카테고리 하나를 무작위로 가지고 온다.
    private CategoryDto randCategory() {
        List<CategoryDto> categories = this.service.getCategories();
        return categories.get(new Random().nextInt(categories.size()));
    }

    // 검증을 위해 브랜드 하나를 무작위로 가지고 온다.
    private BrandDto randBrand() {
        List<BrandDto> brands = this.service.getBrands();
        return brands.get(new Random().nextInt(brands.size()));
    }

    // 검증을 위해 상품 하나를 무작위로 가지고 온다.
    private ProductDto randProduct() {
        List<ProductDto> products = this.service.getProducts();
        return products.get(new Random().nextInt(products.size()));
    }

    @Test
    @DisplayName("domain.category : 카테고리 조회")
    void testGetCategories() {
        List<CategoryDto> actuals = this.service.getCategories();
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
    }

    @Test
    @DisplayName("domain.brand : 브랜드 전체 조회")
    void testGetBrands() {
        List<BrandDto> actuals = this.service.getBrands();
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
    }

    @Test
    @DisplayName("domain.brand : 브랜드 명칭 조회")
    void testGetBrandsByName() {
        String expected = this.randBrand().getName();
        List<BrandDto> actuals = this.service.getBrands(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> assertEquals(expected, actual.getName()));
    }

    @Test
    @DisplayName("domain.brand : 브랜드 이름 조회 - 없는 경우")
    void testGetBrandsByNameNotExist() {
        List<BrandDto> actuals = this.service.getBrands("BRAND NEVER EXISTS!");
        assertNotNull(actuals);
        assertTrue(actuals.size() == 0);
    }

    @Test
    @DisplayName("domain.brand : 브랜드 아이디 조회")
    void testGetBrandById() {
        int expected = this.randBrand().getId();
        Optional<BrandDto> actual = this.service.getBrand(expected);
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get().getId());
    }

    @Test
    @DisplayName("domain.brand : 브랜드 아이디 조회 - 없는 경우")
    void testGetBrandByIdNotExist() {
        Optional<BrandDto> actual = this.service.getBrand(Integer.MAX_VALUE);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 신규브랜드 생성")
    void newBrand() {
        BrandDto expected = BrandDto.builder().name("BRAND NEW").build();
        BrandDto actual = this.service.newBrand(expected);
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 기존 브랜드 수정")
    void updateBrand() {
        BrandDto expected = BrandDto.builder().name("BRAND UPDATED").build();
        BrandDto target = this.randBrand();
        Optional<BrandDto> actual = this.service.updateBrand(target.getId(), expected);
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertEquals(target.getId(), actual.get().getId());
        assertEquals(expected.getName(), actual.get().getName());
    }

    @Test
    void getProducts() {

    }

    @Test
    void getProductsByBrandName() {
    }

    @Test
    void getProductsByBrandId() {
    }

    @Test
    void newProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}