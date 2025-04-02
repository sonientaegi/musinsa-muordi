package com.musinsa.muordi.contents.explore.controller;

import com.musinsa.muordi.contents.explore.dto.*;
import com.musinsa.muordi.contents.explore.service.ExploreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 탐색 도메인 컨트롤러.
 */
@RequestMapping("/api/explore/v1")
@RequiredArgsConstructor
@RestController
public class ExploreController {
    private final ExploreService exploreService;

    @GetMapping("/coordi/affordable")
    public CategoryBrandCheapestResponse getAffordableCoordi() {
        CategoryBrandCheapestDto dto = this.exploreService.getCheapestByCategory();
        return CategoryBrandCheapestResponse.fromDto(dto);
    }

    @GetMapping("/coordi/affordable/brand")
    public BrandCheapestResponse getAffordableBrand() {
        BrandCheapestDto dto = this.exploreService.getCheapestBrand();
        return BrandCheapestResponse.fromDto(dto);
    }

    @GetMapping("/coordi/category/price/range")
    public CategoryPriceRangeResponse getCategoryPriceRange(@RequestParam String name) {
        CategoryPriceRangeDto dto = this.exploreService.PriceRangeofCategoryByName(name);
        return CategoryPriceRangeResponse.fromDto(dto);
    }
}
