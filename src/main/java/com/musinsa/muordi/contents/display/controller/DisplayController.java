package com.musinsa.muordi.contents.display.controller;

import com.musinsa.muordi.contents.display.dto.*;
import com.musinsa.muordi.contents.display.service.DisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 전시 도메인 컨트롤러.
 */
@RequestMapping("/api/display/v1")
@RequiredArgsConstructor
@RestController
public class DisplayController {
    // 전시 서비스
    private final DisplayService displayService;

    // request/response - DTO 맵퍼
    private final CategoryDtoMapper categoryDtoMapper = CategoryDtoMapper.instance;
    private final ShowcaseDtoMapper showcaseDtoMapper = ShowcaseDtoMapper.instance;

    @GetMapping("/category")
    public List<CategoryResponse> getCategories() {
        // 카테고리는 기존의 정렬 순서 보장을 위해 ArrayList 사용.
        List<CategoryResponse> categories = new ArrayList<>();
        this.displayService.getCategories().forEach(category -> {
            categories.add(categoryDtoMapper.toResponse(category));
        });
        return categories;
    }

    @GetMapping("/showcase")
    public List<ShowcaseResponse> getShowcases() {
        return this.displayService.getShowcases().stream().map(this.showcaseDtoMapper::toResponse).toList();
    }

    @GetMapping("/showcase/{id}")
    public ShowcaseResponse getShowcaseById(@PathVariable long id) {
        return this.showcaseDtoMapper.toResponse(this.displayService.getShowcase(id));
    }

    @GetMapping("/showcase/category/{id}")
    public List<ShowcaseResponse> getShowcasesByCategory(@PathVariable int id) {
        return this.displayService.getShowcasesByCategoryId(id).stream().map(showcaseDtoMapper::toResponse).toList();
    }

    @GetMapping("/showcase/category/name/{name}")
    public List<ShowcaseResponse> getShowcasesByCategory(@PathVariable String name) {
        return this.displayService.getShowcasesByCategoryName(name).stream().map(showcaseDtoMapper::toResponse).toList();
    }

    @GetMapping("/showcase/brand/{id}")
    public List<ShowcaseResponse> getShowcasesByBrand(@PathVariable int id) {
        return this.displayService.getShowcasesByBrandId(id).stream().map(showcaseDtoMapper::toResponse).toList();
    }

    @GetMapping("/showcase/brand/name/{name}")
    public List<ShowcaseResponse> getShowcasesByBrand(@PathVariable String name) {
        return this.displayService.getShowcasesByBrandName(name).stream().map(showcaseDtoMapper::toResponse).toList();
    }

    @PostMapping("/showcase")
    public ShowcaseResponse createShowcase(@RequestBody ShowcaseCreateRequest request) {
        return this.showcaseDtoMapper
                .toResponse(this.displayService.createShowcase(request.getProductId(), request.getCategoryId()));
    }

    @PutMapping("/showcase/{id}")
    public ShowcaseResponse updateShowcase(@PathVariable long id, @RequestBody ShowcaseUpdateRequest request) {
        return this.showcaseDtoMapper
                .toResponse(this.displayService.updateShowcase(id, request.getCategoryId()));
    }

    @DeleteMapping("/showcase/{id}")
    public void deleteShowcase(@PathVariable long id) {
        this.displayService.deleteShowcase(id);
    }
}
