package com.musinsa.muordi.platform.admin.service;

import com.musinsa.muordi.common.exception.NotFoundException;
import com.musinsa.muordi.platform.admin.dto.BrandDto;
import com.musinsa.muordi.platform.admin.dto.ProductDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test-admin")
@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService service;

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
    @DisplayName("브랜드 전체 조회")
    void testGetBrands() {
        List<BrandDto> actuals = this.service.getBrands();
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
    }

    @Test
    @DisplayName("브랜드 이름 조회")
    void testGetBrandsByName() {
        String expected = this.randBrand().getName();
        List<BrandDto> actuals = this.service.getBrands(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> assertEquals(expected, actual.getName()));
    }

    @Test
    @DisplayName("브랜드 이름 조회 - 없는 이름")
    void testGetBrandsByNameNotExists() {
        List<BrandDto> actuals = this.service.getBrands("BRAND NEVER EXISTS!");
        assertNotNull(actuals);
        assertEquals(0, actuals.size());
    }

    @Test
    @DisplayName("브랜드 ID 조회")
    void testGetBrandById() {
        int expected = this.randBrand().getId();
        BrandDto actual = this.service.getBrand(expected);
        assertNotNull(actual);
        assertEquals(expected, actual.getId());
    }

    @Test
    @DisplayName("브랜드 ID 조회 - 없는 ID")
    void testGetBrandByIdNotExists() {
        assertThrows(NotFoundException.class, () -> this.service.getBrand(Integer.MAX_VALUE));
    }

    @Test
    @Transactional
    @DisplayName("브랜드 생성")
    void testCreateBrand() {
        BrandDto expected = new BrandDto(null, "BRAND NEW");
        BrandDto actual = this.service.createBrand(expected);
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @Transactional
    @DisplayName("브랜드 수정")
    void testUpdateBrand() {
        int target = this.randBrand().getId();
        BrandDto expected = new BrandDto(null, "BRAND UPDATED");
        BrandDto actual = this.service.updateBrand(target, expected);
        assertNotNull(actual);
        assertEquals(target, actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @Transactional
    @DisplayName("브랜드 수정 - 없는 ID")
    void testUpdateBrandNotExists() {
        int target = Integer.MAX_VALUE;
        BrandDto expected = new BrandDto(null, "BRAND UPDATED");
        assertThrows(NotFoundException.class, () -> this.service.updateBrand(target, expected));
    }

    @Test
    @Transactional
    @DisplayName("브랜드 삭제")
    void testDeleteBrand() {
        BrandDto target = this.randBrand();
        this.service.deleteBrand(target.getId());
        assertThrows(NotFoundException.class, () -> this.service.getBrand(target.getId()));
    }

    @Test
    @DisplayName("상품 ID 조회")
    void testGetProduct() {
        ProductDto expected = this.randProduct();
        ProductDto actual = this.service.getProduct(expected.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void testGetProducts() {
        List<ProductDto> actuals = this.service.getProducts();
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
    }

    @Test
    @DisplayName("브랜드 이름으로 상품 조회")
    void testGetProductsByBrandName() {
        String expected = this.randBrand().getName();
        List<ProductDto> actuals = this.service.getProductsByBrandName(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> assertEquals(expected, actual.getBrandName()));
    }

    @Test
    @DisplayName("브랜드 이름으로 상품 조회 - 없는 경우")
    void testGetProductsByBrandNameNotExist() {
        List<ProductDto> actuals = this.service.getProductsByBrandName("BRAND NEVER EXISTS!");
        assertNotNull(actuals);
        assertEquals(0, actuals.size());
    }

    @Test
    @DisplayName("브랜드 ID로 상품 조회")
    void testGetProductsByBrandId() {
        int expected = this.randBrand().getId();
        List<ProductDto> actuals = this.service.getProductsByBrandId(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> assertEquals(expected, actual.getBrandId()));
    }

    @Test
    @DisplayName("브랜드 ID로 상품 조회 - 없는 경우")
    void testGetProductsByBrandIdNotExist() {
        List<ProductDto> actuals = this.service.getProductsByBrandId(Integer.MAX_VALUE);
        assertNotNull(actuals);
        assertTrue(actuals.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName(" 상품 생성")
    void testCreateProduct() {
        BrandDto expectedBrand = this.randBrand();
        ProductDto expected = new ProductDto();
        expected.setBrandId(expectedBrand.getId());
        expected.setPrice(new Random().nextInt(100000));

        ProductDto actual = this.service.createProduct(expected);
        assertNotNull(actual);
        assertEquals(expected.getBrandId(), actual.getBrandId());
        assertEquals(expectedBrand.getName(), actual.getBrandName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    @Transactional
    @DisplayName("상품 수정")
    void testUpdateProduct() {
        // 무작위로 상품을 가져온 다음 무작위로 브랜드를 변경한다.
        ProductDto target = this.randProduct();
        BrandDto expectedBrand = this.randBrand();
        while (expectedBrand.getId() == target.getBrandId()) {
            expectedBrand = this.randBrand();
        }
        ProductDto expected = new ProductDto();
        expected.setBrandId(expectedBrand.getId());
        expected.setPrice(new Random().nextInt(100000));

        ProductDto actual = this.service.updateProduct(target.getId(), expected);
        assertNotNull(actual);
        assertEquals(target.getId(), actual.getId());
        assertEquals(expectedBrand.getId(), actual.getBrandId());
        assertEquals(expectedBrand.getName(), actual.getBrandName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    @Transactional
    @DisplayName("상품 수정 - 없는 ID")
    void testUpdateProductNotExists() {
        long target = Long.MAX_VALUE;
        ProductDto expected = new ProductDto();
        expected.setBrandId(this.randBrand().getId());
        expected.setPrice(new Random().nextInt(100000));

        assertThrows(NotFoundException.class, () -> this.service.updateProduct(target, expected));
    }

    @Test
    @Transactional
    @DisplayName("상품 수정 - 브랜드가 없는 경우")
    void testUpdateProductWrongBrand() {
        ProductDto target = this.randProduct();
        target.setBrandId(Integer.MAX_VALUE);
        assertThrows(NotFoundException.class, () -> this.service.updateProduct(target.getId(), target));
    }

    @Test
    @Transactional
    @DisplayName("상품 삭제")
    void testDeleteProduct() {
        long target = this.randProduct().getId();
        this.service.deleteProduct(target);
        assertThrows(NotFoundException.class, ()-> this.service.getProduct(target));
    }
}