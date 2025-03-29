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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DomainServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private DomainService service;

    private List<Brand> testBrands;

    // 검증을 위한 데이터를 생성한다.
    @BeforeEach
    public void prepareTestDB() {
        // 테스트용 브랜드 생성
        this.testBrands = this.brandRepository.saveAll(
                List.of(
                        Brand.builder().name("BRAND 1").build(),
                        Brand.builder().name("BRAND 2").build(),
                        Brand.builder().name("BRAND NAME DUPLICATED").build(),
                        Brand.builder().name("BRAND NAME DUPLICATED").build(),
                        Brand.builder().name("BRAND 3").build(),
                        Brand.builder().name("BRAND 4").build()
                )
        );
        this.testBrands.sort(Comparator.comparingInt(Brand::getId));

        // 테스트용 상품 생성
        // ...
    }

    @Test
    @Transactional
    @DisplayName("domain.category : 카테고리 조회")
    void testGetCategories() {
        List<CategoryDto> actuals = this.service.getCategories();
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);

        List<CategoryDto> expecteds = CategoryDto.fromEntities(this.categoryRepository.findAll());
        assertTrue(actuals.containsAll(expecteds));
        assertTrue(expecteds.containsAll(actuals));
    }


    @Test
    @Transactional
    @DisplayName("domain.brand : 브랜드 전체 조회")
    void testGetBrands() {
        List<BrandDto> expecteds = BrandDto.fromEntities(this.testBrands);
        List<BrandDto> actuals = this.service.getBrands();
        assertNotNull(actuals);
        assertTrue(actuals.containsAll(expecteds));
        assertTrue(expecteds.containsAll(actuals));
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 브랜드 명칭 조회")
    void testGetBrandsByName() {
        String expected = this.testBrands.get(3).getName();
        List<BrandDto> actuals = this.service.getBrands(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> assertEquals(expected, actual.getName()));
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 브랜드 이름 조회 - 없는 경우")
    void testGetBrandsByNameNotExist() {
        List<BrandDto> actuals = this.service.getBrands("BRAND NEVER EXISTS!");
        assertNotNull(actuals);
        assertTrue(actuals.size() == 0);
    }

    @Test
    @Transactional
    @DisplayName("domain.brand : 브랜드 아이디 조회")
    void testGetBrandById() {
        BrandDto expected = BrandDto.fromEntity(this.testBrands.get(2));
        BrandDto actual = this.service.getBrand(expected.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
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
    void updateBrand() {
        BrandDto expected = BrandDto.builder().name("BRAND UPDATED").build();
        Brand target = this.testBrands.get(0);
        BrandDto actual = this.service.updateBrand(target.getId(), expected).orElse(null);
        assertNotNull(actual);
        assertEquals(target.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }
}