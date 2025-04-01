package com.musinsa.muordi.contents.display.dto;

import com.musinsa.muordi.contents.display.repository.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryDtoMapper {
    CategoryDtoMapper instance = Mappers.getMapper(CategoryDtoMapper.class);

    CategoryDto fromEntity(Category entity);
    CategoryResponse toResponse(CategoryDto dto);
}
