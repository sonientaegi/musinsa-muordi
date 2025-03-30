package com.musinsa.muordi.platform.admin.dto;

import com.musinsa.muordi.platform.admin.dto.service.BrandDto;
import com.musinsa.muordi.platform.admin.repository.Brand;
import com.musinsa.muordi.platform.admin.repository.BrandRepositoryTest;
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
        List<Brand> brands = List.of(
                BrandRepositoryTest.sample(),
                BrandRepositoryTest.sample(),
                BrandRepositoryTest.sample(),
                BrandRepositoryTest.sample()
        );

        List<BrandDto> brandDtos = BrandDto.fromEntities(brands);
        for (int i = 0; i < brands.size(); i++) {
            Brand brand = brands.get(i);
            BrandDto brandDto = brandDtos.get(i);

            assertEquals(brand.getId(), brandDto.getId());
            assertEquals(brand.getName(), brandDto.getName());
        }
    }
}