package com.musinsa.muordi.platform.admin.dto;

import com.musinsa.muordi.platform.admin.repository.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BrandDtoMapper {
    BrandDtoMapper instance = Mappers.getMapper(BrandDtoMapper.class);

    BrandDto fromEntity(Brand brand);

//    @Mapping(target = "products", ignore = true)
    Brand toEntity(BrandDto brandDto);

    BrandResponse toResponse(BrandDto brandDto);

    BrandDto fromRequest(BrandRequest request);
}
