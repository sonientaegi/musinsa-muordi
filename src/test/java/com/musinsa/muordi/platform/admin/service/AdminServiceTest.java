package com.musinsa.muordi.platform.admin.service;

import com.musinsa.muordi.platform.admin.dto.BrandDto;
import com.musinsa.muordi.platform.admin.dto.ProductDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test-admin")
@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService service;

//    // 검증을 위해 카테고리 하나를 무작위로 가지고 온다.
//    private CategoryDto randCategory() {
//        List<CategoryDto> categories = this.service.getCategories();
//        return categories.get(new Random().nextInt(categories.size()));
//    }

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
//
//    @Test
//    @DisplayName("domain.category : 카테고리 조회")
//    void testGetCategories() {
//        List<CategoryDto> actuals = this.service.getCategories();
//        assertNotNull(actuals);
//        assertTrue(actuals.size() > 0);
//    }

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
    void testGetBrandsByNameNotExists() {
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
    void testGetBrandByIdNotExists() {
        Optional<BrandDto> actual = this.service.getBrand(Integer.MAX_VALUE);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 신규브랜드 생성")
    void testNewBrand() {
        BrandDto expected = BrandDto.builder().name("BRAND NEW").build();
        BrandDto actual = this.service.newBrand(expected);
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 기존 브랜드 수정")
    void testUpdateBrand() {
        int target = this.randBrand().getId();
        BrandDto expected = BrandDto.builder().name("BRAND UPDATED").build();
        Optional<BrandDto> actual = this.service.updateBrand(target, expected);
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertEquals(target, actual.get().getId());
        assertEquals(expected.getName(), actual.get().getName());
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 기존 브랜드 수정 - 없는 경우")
    void testUpdateBrandNotExists() {
        int target = Integer.MAX_VALUE;
        BrandDto expected = BrandDto.builder().name("BRAND UPDATED").build();
        Optional<BrandDto> actual = this.service.updateBrand(target, expected);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 브랜드 삭제")
    void testDeleteBrand() {
//        // 삭제 테스트는 신규 생성 후 수행.
//        BrandDto target = this.service.newBrand(BrandDto.builder().name("BRAND FOR DELETE").build());
        BrandDto target = this.randBrand();
        this.service.deleteBrand(target.getId());
        Optional<BrandDto> actual = this.service.getBrand(target.getId());
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("domain.product : 상품 단건 조회")
    void testGetProduct() {
        ProductDto expected = this.randProduct();
        Optional<ProductDto> actual = this.service.getProduct(expected.getId());
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    @DisplayName("domain.product : 상품 전체 조회")
    void testGetProducts() {
        List<ProductDto> actuals = this.service.getProducts();
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
    }

    @Test
    @DisplayName("domain.product : 브랜드 명칭으로 상품 조회")
    void testGetProductsByBrandName() {
        String expected = this.randBrand().getName();
        List<ProductDto> actuals = this.service.getProductsByBrandName(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> assertEquals(expected, actual.getBrandName()));
    }

    @Test
    @DisplayName("domain.product : 브랜드 명칭으로 상품 조회 - 없는 경우")
    void testGetProductsByBrandNameNotExist() {
        List<ProductDto> actuals = this.service.getProductsByBrandName("BRAND NEVER EXISTS!");
        assertNotNull(actuals);
        assertTrue(actuals.size() == 0);
    }

    @Test
    @DisplayName("domain.product : 브랜드 식별자로 상품 조회")
    void testGetProductsByBrandId() {
        int expected = this.randBrand().getId();
        List<ProductDto> actuals = this.service.getProductsByBrandId(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> assertEquals(expected, actual.getBrandId()));
    }

    @Test
    @DisplayName("domain.product : 브랜드 식별자로 상품 조회 - 없는 경우")
    void testGetProductsByBrandIdNotExist() {
        List<ProductDto> actuals = this.service.getProductsByBrandId(Integer.MAX_VALUE);
        assertNotNull(actuals);
        assertTrue(actuals.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("domain.product : 신규 상품 생성")
    void testNewProduct() {
        BrandDto expectedBrand = this.randBrand();
        ProductDto expected = ProductDto.builder()
                .brandId(expectedBrand.getId())
                .price(new Random().nextInt(100000))
                .build();
        ProductDto actual = this.service.newProduct(expected);
        assertNotNull(actual);
        assertEquals(expected.getBrandId(), actual.getBrandId());
        assertEquals(expectedBrand.getName(), actual.getBrandName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    @Transactional
    @DisplayName("domain.product : 기존 상품 수정")
    void testUpdateProduct() {
        ProductDto target = this.randProduct();
        BrandDto expectedBrand = this.randBrand();
        while (expectedBrand.getId() == target.getBrandId()) {
            expectedBrand = this.randBrand();
        }
        ProductDto expected = ProductDto.builder()
                .brandId(expectedBrand.getId())
                .price(new Random().nextInt(100000))
                .build();
        Optional<ProductDto> actual = this.service.updateProduct(target.getId(), expected);
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertEquals(target.getId(), actual.get().getId());
        assertEquals(expectedBrand.getId(), actual.get().getBrandId());
        assertEquals(expectedBrand.getName(), actual.get().getBrandName());
        assertEquals(expected.getPrice(), actual.get().getPrice());
    }

    @Test
    @Transactional
    @DisplayName("domain.product : 기존 상품 수정 - 없는 경우")
    void testUpdateProductNotExists() {
        long target = Long.MAX_VALUE;
        ProductDto expected = ProductDto.builder()
                .brandId(this.randBrand().getId())
                .price(new Random().nextInt(100000))
                .build();
        Optional<ProductDto> actual = this.service.updateProduct(target, expected);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    // TODO 브랜드 없는 경우에 대한 정의 필요
    @Test
    @Transactional
    @DisplayName("domain.product : 기존 상품 수정 - 브랜드가 없는 경우")
    void testUpdateProductWrongBrand() {
        ProductDto target = this.randProduct();
        target.setBrandId(Integer.MAX_VALUE);
        Optional<ProductDto> actual = this.service.updateProduct(target.getId(), target);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("domain.product : 상품 삭제")
    void testDeleteProduct() {
        long target = this.randProduct().getId();
        this.service.deleteProduct(target);
        Optional<ProductDto> actual = this.service.getProduct(target);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }
}