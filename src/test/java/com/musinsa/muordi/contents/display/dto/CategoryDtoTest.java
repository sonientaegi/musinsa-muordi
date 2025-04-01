package com.musinsa.muordi.contents.display.dto;

import com.musinsa.muordi.contents.display.repository.Category;
import com.musinsa.muordi.contents.display.repository.CategoryRepositoryJpaTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryDtoTest {
    // Category -> DTO 변환 검증.
    @Test
    void fromEntity() {
        Category category = CategoryRepositoryJpaTest.sample();

        CategoryDto categoryDto = CategoryDto.fromEntity(category);
        assertEquals(category.getId(), categoryDto.getId());
        assertEquals(category.getName(), categoryDto.getName());
        assertEquals(category.getDisplaySequence(), categoryDto.getDisplaySequence());
    }

    // Category 리스트 -> DTO 리스트 변환 검증.
    @Test
    void fromEntities() {
        List<Category> categories = List.of(
                CategoryRepositoryJpaTest.sample(),
                CategoryRepositoryJpaTest.sample(),
                CategoryRepositoryJpaTest.sample(),
                CategoryRepositoryJpaTest.sample()
        );

        List<CategoryDto> categoryDtos = CategoryDto.fromEntities(categories);
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            CategoryDto categoryDto = categoryDtos.get(i);

            assertEquals(category.getId(), categoryDto.getId());
            assertEquals(category.getName(), categoryDto.getName());
            assertEquals(category.getDisplaySequence(), categoryDto.getDisplaySequence());
        }
    }
}