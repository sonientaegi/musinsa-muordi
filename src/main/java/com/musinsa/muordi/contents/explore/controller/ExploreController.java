package com.musinsa.muordi.contents.explore.controller;

import com.musinsa.muordi.contents.explore.dto.BrandCheapestDto;
import com.musinsa.muordi.contents.explore.dto.CategoryBrandCheapestDto;
import com.musinsa.muordi.contents.explore.dto.CategoryPriceRangeDto;
import com.musinsa.muordi.contents.explore.service.ExploreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/explore/v1")
@RequiredArgsConstructor
@RestController
public class ExploreController {
    private final ExploreService exploreService;

    @GetMapping("/coordi/affordable")
    public CategoryBrandCheapestDto getAffordableCoordi() {
        return this.exploreService.getCheapestByCategory();
    }

    @GetMapping("/coordi/affordable/brand")
    public BrandCheapestDto getAffordableBrand() {
        return this.exploreService.getCheapestBrand();
    }

    @GetMapping("/coordi/category/{name}/price/range")
    public CategoryPriceRangeDto getCategoryPriceRange(@PathVariable String name) {
        return this.exploreService.PriceRangeofCategoryByName(name);
    }
}
