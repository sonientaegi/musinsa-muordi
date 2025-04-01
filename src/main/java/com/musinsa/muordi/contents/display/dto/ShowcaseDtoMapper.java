package com.musinsa.muordi.contents.display.dto;

import com.musinsa.muordi.contents.display.repository.Category;
import com.musinsa.muordi.contents.display.repository.Showcase;
import com.musinsa.muordi.platform.admin.repository.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShowcaseDtoMapper {
    ShowcaseDtoMapper instance = Mappers.getMapper(ShowcaseDtoMapper.class);

    @Mapping(target="productId", expression="java(entity.getProduct().getId())")
    @Mapping(target="price", expression="java(entity.getProduct().getPrice())")
    @Mapping(target="categoryId", expression="java(entity.getCategory().getId())")
    @Mapping(target="categoryName", expression="java(entity.getCategory().getName())")
    @Mapping(target="brandId", expression="java(entity.getProduct().getBrand().getId())")
    @Mapping(target="brandName", expression="java(entity.getProduct().getBrand().getName())")
    ShowcaseDto fromEntity(Showcase entity);

    @Mapping(target="product", source="product")
    @Mapping(target="category", source="category")
    Showcase toEntity(Product product, Category category);

    ShowcaseResponse toResponse(ShowcaseDto dto);

    ShowcaseDto fromRequest(ShowcaseCreateRequest request);

    ShowcaseDto fromRequest(ShowcaseUpdateRequest request);
}
