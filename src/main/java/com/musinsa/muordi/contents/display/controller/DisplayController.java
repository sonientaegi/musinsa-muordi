package com.musinsa.muordi.contents.display.controller;

import com.musinsa.muordi.contents.display.dto.*;
import com.musinsa.muordi.contents.display.service.DisplayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 전시 도메인 컨트롤러.
 */
@Tag(name="Display", description="전시 서비스 API")
@RequestMapping("/api/display/v1")
@RequiredArgsConstructor
@RestController
public class DisplayController {
    // 전시 서비스
    private final DisplayService displayService;

    // request/response - DTO 맵퍼
    private final CategoryDtoMapper categoryDtoMapper = CategoryDtoMapper.instance;
    private final ShowcaseDtoMapper showcaseDtoMapper = ShowcaseDtoMapper.instance;

    @Operation(summary = "전시 카테고리 조회")
    @ApiResponse(responseCode = "200", description = "OK. 전시 순서 오름차순으로 정렬한 결과를 반환한다.")
    @GetMapping("/category")
    public List<CategoryResponse> getCategories() {
        // 카테고리는 기존의 정렬 순서 보장을 위해 ArrayList 사용.
        List<CategoryResponse> categories = new ArrayList<>();
        this.displayService.getCategories().forEach(category -> {
            categories.add(categoryDtoMapper.toResponse(category));
        });
        return categories;
    }

    @Operation(summary = "전체 전시상품 조회")
    @GetMapping("/showcase")
    public List<ShowcaseResponse> getShowcases() {
        return this.displayService.getShowcases().stream().map(this.showcaseDtoMapper::toResponse).toList();
    }

    @Operation(summary = "전시상품 조회")
    @Parameter(name="id", description = "상품 ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "조회 대상이 없다.", content = @Content())
    @GetMapping("/showcase/{id}")
    public ShowcaseResponse getShowcaseById(@PathVariable long id) {
        return this.showcaseDtoMapper.toResponse(this.displayService.getShowcase(id));
    }

    @Operation(summary = "카테고리 ID의 전시상품 조회")
    @Parameter(name="id", description = "카테고리 ID")
    @GetMapping("/showcase/category/{id}")
    public List<ShowcaseResponse> getShowcasesByCategory(@PathVariable int id) {
        return this.displayService.getShowcasesByCategoryId(id).stream().map(showcaseDtoMapper::toResponse).toList();
    }

    @Operation(summary = "카테고리 이름의 조회")
    @Parameter(name="id", description = "카테고리 이름")
    @GetMapping("/showcase/category/name/{name}")
    public List<ShowcaseResponse> getShowcasesByCategory(@PathVariable String name) {
        return this.displayService.getShowcasesByCategoryName(name).stream().map(showcaseDtoMapper::toResponse).toList();
    }

    @Operation(summary = "브랜드 ID의 전시상품 조회")
    @Parameter(name="id", description = "브랜드 ID")
    @GetMapping("/showcase/brand/{id}")
    public List<ShowcaseResponse> getShowcasesByBrand(@PathVariable int id) {
        return this.displayService.getShowcasesByBrandId(id).stream().map(showcaseDtoMapper::toResponse).toList();
    }

    @Operation(summary = "브랜드 이름의 전시상품 조회")
    @Parameter(name="id", description = "브랜드 이름")
    @GetMapping("/showcase/brand/name/{name}")
    public List<ShowcaseResponse> getShowcasesByBrand(@PathVariable String name) {
        return this.displayService.getShowcasesByBrandName(name).stream().map(showcaseDtoMapper::toResponse).toList();
    }

    @Operation(summary = "전시상품 등록")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "해당 상품이 이미 전시중이므로 등록할 수 없다.", content = @Content())
    @PostMapping("/showcase")
    public ShowcaseResponse createShowcase(@RequestBody ShowcaseCreateRequest request) {
        return this.showcaseDtoMapper
                .toResponse(this.displayService.createShowcase(request.getProductId(), request.getCategoryId()));
    }

    @Operation(summary = "전시상품 수정")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "수정 대상이 없다.", content = @Content())
    @PutMapping("/showcase/{id}")
    public ShowcaseResponse updateShowcase(@PathVariable long id, @RequestBody ShowcaseUpdateRequest request) {
        return this.showcaseDtoMapper
                .toResponse(this.displayService.updateShowcase(id, request.getCategoryId()));
    }

    @Operation(summary = "상품 전시 해제")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "해제 대상이 없다.", content = @Content())
    @DeleteMapping("/showcase/{id}")
    public void deleteShowcase(@PathVariable long id) {
        this.displayService.deleteShowcase(id);
    }
}
