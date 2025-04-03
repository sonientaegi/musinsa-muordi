package com.musinsa.muordi.platform.admin.controller;

import com.musinsa.muordi.platform.admin.dto.*;
import com.musinsa.muordi.platform.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자 도메인 컨트롤러.
 */
@Tag(name="Admin", description="기본 정보 관리용 API")
@RequestMapping("/api/admin/v1")
@RequiredArgsConstructor
@RestController
public class AdminController {
    // 어드민 서비스
    private final AdminService adminService;

    // request/response - DTO 맵퍼
    private final BrandDtoMapper brandMapper = BrandDtoMapper.instance;
    private final ProductDtoMapper productMapper = ProductDtoMapper.instance;

    @Operation(summary = "전체 브랜드 조회")
    // @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = BrandResponse.class)))
    @GetMapping("/brand")
    public List<BrandResponse> getBrand() {
        return this.adminService.getBrands().stream().map(this.brandMapper::toResponse).toList();
    }

    @Operation(summary = "브랜드 조회")
    @Parameter(name="id", description = "브랜드 ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "조회 대상이 없다.", content = @Content())
    @GetMapping("/brand/{id}")
    public BrandResponse getBrand(@PathVariable int id) {
        return this.brandMapper.toResponse(this.adminService.getBrand(id));
    }

    @Operation(summary = "브랜드 조회")
    @Parameter(name="name", description = "브랜드 이름")
    @ApiResponse(responseCode = "200", description = "OK. 같은 이름의 브랜드가 여러개 있다면 모두 반환한다.")
    @GetMapping("/brand/name/{name}")
    public List<BrandResponse> getBrandsByName(@PathVariable String name) {
        return this.adminService.getBrands(name).stream().map(this.brandMapper::toResponse).toList();
    }

    @Operation(summary = "브랜드 생성")
    @ApiResponse(responseCode = "200", description = "OK. ID를 포함한 생성 결과를 반환한다.")
    @PostMapping("/brand")
    public BrandResponse createBrand(@RequestBody BrandRequest request) {
        BrandDto newBrandDto = this.adminService.createBrand(this.brandMapper.fromRequest(request));
        return this.brandMapper.toResponse(newBrandDto);
    }

    @Operation(summary = "브랜드 수정")
    @Parameter(name="id", description = "브랜드 ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "수정 대상이 없다.", content = @Content())
    @PutMapping("/brand/{id}")
    public BrandResponse updateBrand(@PathVariable int id, @RequestBody BrandDto brandDto) {
        return this.brandMapper.toResponse(this.adminService.updateBrand(id, brandDto));
    }

    @Operation(summary = "브랜드 삭제")
    @Parameter(name="id", description = "브랜드 ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "삭제 대상이 없다.", content = @Content())
    @ApiResponse(responseCode = "500", description = "삭제 불가.", content = @Content())
    @DeleteMapping("/brand/{id}")
    public void deleteById(@PathVariable int id) {
        this.adminService.deleteBrand(id);
    }

    @Operation(summary = "전체 상품 조회")
    @GetMapping("/product")
    public List<ProductResponse> getProducts() {
        return this.adminService.getProducts().stream().map(this.productMapper::toResponse).toList();
    }

    @Operation(summary = "상품 조회")
    @Parameter(name="id", description = "상품 ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "조회 대상이 없다.", content = @Content())
    @GetMapping("/product/{id}")
    public ProductResponse getProduct(@PathVariable int id) {
        return this.productMapper.toResponse(this.adminService.getProduct(id));
    }

    @Operation(summary = "브랜드 ID로 상품 조회")
    @Parameter(name="id", description = "브랜드 ID")
    @GetMapping("/product/brand/{id}")
    public List<ProductResponse> getProductsByBrand(@PathVariable int id) {
        return this.adminService.getProductsByBrandId(id).stream().map(this.productMapper::toResponse).toList();
    }

    @Operation(summary = "브랜드 이름으로 상품 조회")
    @Parameter(name="name", description = "브랜드 이름")
    @ApiResponse(responseCode = "200", description = "OK. 입력한 이름의 브랜드가 판매중인 모든 상품")
    @GetMapping("/product/brand/name/{name}")
    public List<ProductResponse> getProductsByBrandName(@PathVariable String name) {
        return this.adminService.getProductsByBrandName(name).stream().map(this.productMapper::toResponse).toList();
    }

    @Operation(summary = "상품 생성")
    @ApiResponse(responseCode = "200", description = "OK. ID를 포함한 생성 결과를 반환한다.")
    @PostMapping("/product")
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return this.productMapper.toResponse(this.adminService.createProduct(this.productMapper.fromRequest(request)));
    }

    @Operation(summary = "상품 수정")
    @Parameter(name="id", description = "상품 ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "수정 대상이 없다.", content = @Content())
    @PutMapping("/product/{id}")
    public ProductResponse updateProduct(@PathVariable long id, @RequestBody ProductRequest request) {
        return this.productMapper.toResponse(
                this.adminService.updateProduct(id, this.productMapper.fromRequest(request))
        );
    }

    @Operation(summary = "상품 삭제")
    @Parameter(name="id", description = "상품 ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "삭제 대상이 없다.", content = @Content())
    @ApiResponse(responseCode = "500", description = "삭제 불가.", content = @Content())
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        this.adminService.deleteProduct(id);
    }
}
