package com.musinsa.muordi.platform.admin.dto;

import com.musinsa.muordi.platform.admin.repository.Brand;
import com.musinsa.muordi.platform.admin.repository.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ProductDtoMapper {
    ProductDtoMapper instance = Mappers.getMapper(ProductDtoMapper.class);

    @Mapping(target="brandId", expression="java(entity.getBrand().getId())")
    @Mapping(target="brandName", expression="java(entity.getBrand().getName())")
    ProductDto fromEntity(Product entity);

    @Mapping(target="brand", source="brand")
    Product toEntity(ProductDto productDto, Brand brand);

    ProductResponse toResponse(ProductDto dto);

    ProductDto fromRequest(ProductRequest request);
}
