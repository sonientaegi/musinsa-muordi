package com.musinsa.muordi.contents.explore.controller;

import com.musinsa.muordi.contents.explore.dto.*;
import com.musinsa.muordi.contents.explore.service.ExploreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * 탐색 도메인 컨트롤러.
 */
@Tag(name="Explore", description="통계 및 검색 API")
@RequestMapping("/api/explore/v1")
@RequiredArgsConstructor
@RestController
public class ExploreController {
    private final ExploreService exploreService;

    @Operation(summary = "카테고리 별 최저 가격 브랜드 조회", description = "카테고리 별 최저 가격 브랜드와 상품의 가격, 그리고 총액을 조회한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "500", description = "전시중인 상품이 하나도 없다. 관리자 확인 필요.", content = @Content())
    @GetMapping("/coordi/affordable")
    public CategoryBrandCheapestResponse getAffordableCoordi() {
        CategoryBrandCheapestDto dto = this.exploreService.getCheapestByCategory();
        return CategoryBrandCheapestResponse.fromDto(dto);
    }

    @Operation(summary = "단일 브랜드로 모든 카테고리 상품 구매 시 최저 가격 브랜드 조회", description = "합계 최저가 판매중인 브랜드 기준으로 카테고리 별 상품 금액과 합계 금액을 조회한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "500", description = "전시중인 상품이 하나도 없거나, 전시 조건(모든 카테고리에 1개 이상의 상품을 전시)을 만족하는 브랜드가 하나도 없다. 관리자 확인 필요.", content = @Content())
    @GetMapping("/coordi/affordable/brand")
    public BrandCheapestResponse getAffordableBrand() {
        BrandCheapestDto dto = this.exploreService.getCheapestBrand();
        return BrandCheapestResponse.fromDto(dto);
    }

    @Operation(summary = "카테고리 이름으로 최저, 최고가격 조회", description = "입력받은 카테고리로 판매중인 최고, 최저가 상품의 금액과 브랜드를 조회한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "요청한 카테고리가 존재하지 않는다.", content = @Content())
    @ApiResponse(responseCode = "500", description = "전시중인 상품이 하나도 없다. 관리자 확인 필요.", content = @Content())
    @GetMapping("/coordi/category/price/range")
    public CategoryPriceRangeResponse getCategoryPriceRange(@RequestParam String name) {
        CategoryPriceRangeDto dto = this.exploreService.PriceRangeofCategoryByName(name);
        return CategoryPriceRangeResponse.fromDto(dto);
    }
}
