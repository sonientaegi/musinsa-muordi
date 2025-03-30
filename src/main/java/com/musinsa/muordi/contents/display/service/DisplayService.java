package com.musinsa.muordi.contents.display.service;

import com.musinsa.muordi.contents.display.dto.CategoryDto;
import com.musinsa.muordi.contents.display.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DisplayService {
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 목록을 조회한다. 조회결과는 항상 전시순서 오름차순으로 정렬되어있다.
     *
     * @return 카테고리 DTO 리스트를 반환한다.
     */
    public List<CategoryDto> getCategories() {
        return CategoryDto.fromEntities(this.categoryRepository.findAll());
    }

}