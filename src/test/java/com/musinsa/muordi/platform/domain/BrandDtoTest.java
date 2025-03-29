package com.musinsa.muordi.platform.domain;

import com.musinsa.muordi.platform.domain.brand.Brand;
import com.musinsa.muordi.platform.domain.brand.BrandRepositoryTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandDtoTest {
    // Brand -> DTO 변환 검증.
    @Test
    void fromEntity() {
        Brand brand = BrandRepositoryTest.sample();

        BrandDto brandDto = BrandDto.fromEntity(brand);
        assertEquals(brand.getId(), brandDto.getId());
        assertEquals(brand.getName(), brandDto.getName());
    }

    // Brand 리스트 -> DTO 리스트 변환 검증.
    @Test
    void fromEntities() {
        List<Brand> categories = List.of(
                BrandRepositoryTest.sample(),
                BrandRepositoryTest.sample(),
                BrandRepositoryTest.sample(),
                BrandRepositoryTest.sample()
        );

        List<BrandDto> brandDtos = BrandDto.fromEntities(categories);
        for (int i = 0; i < categories.size(); i++) {
            Brand brand = categories.get(i);
            BrandDto brandDto = brandDtos.get(i);

            assertEquals(brand.getId(), brandDto.getId());
            assertEquals(brand.getName(), brandDto.getName());
        }
    }
}