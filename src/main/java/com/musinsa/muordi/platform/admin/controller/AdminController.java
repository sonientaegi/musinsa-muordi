package com.musinsa.muordi.platform.admin.controller;

import com.musinsa.muordi.platform.admin.dto.*;
import com.musinsa.muordi.platform.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/v1")
@RequiredArgsConstructor
@RestController
public class AdminController {
    // 어드민 서비스
    private final AdminService adminService;

    // request/response - DTO 맵퍼
    private final BrandDtoMapper brandMapper = BrandDtoMapper.instance;
    private final ProductDtoMapper productMapper = ProductDtoMapper.instance;

    @GetMapping("/brand")
    public List<BrandResponse> getBrand() {
        return this.adminService.getBrands().stream().map(this.brandMapper::toResponse).toList();
    }

    @PostMapping("/brand")
    public BrandResponse createBrand(@RequestBody BrandRequest request) {
        BrandDto newBrandDto = this.adminService.createBrand(this.brandMapper.fromRequest(request));
        return this.brandMapper.toResponse(newBrandDto);
    }

    @GetMapping("/brand/{id}")
    public BrandResponse getBrand(@PathVariable int id) {
        return this.brandMapper.toResponse(this.adminService.getBrand(id));
    }

    @PutMapping("/brand/{id}")
    public BrandResponse updateBrand(@PathVariable int id, @RequestBody BrandDto brandDto) {
        return this.brandMapper.toResponse(this.adminService.updateBrand(id, brandDto));
    }

    @DeleteMapping("/brand/{id}")
    public void deleteById(@PathVariable int id) {
        this.adminService.deleteBrand(id);
    }

    @GetMapping("/product")
    public List<ProductResponse> getProducts() {
        return this.adminService.getProducts().stream().map(this.productMapper::toResponse).toList();
    }

    @GetMapping("/product/{id}")
    public ProductResponse getProduct(@PathVariable int id) {
        return this.productMapper.toResponse(this.adminService.getProduct(id));
    }

    @GetMapping("/product/brand/{id}")
    public List<ProductResponse> getProductsByBrand(@PathVariable int id) {
        return this.adminService.getProductsByBrandId(id).stream().map(this.productMapper::toResponse).toList();
    }

    @GetMapping("/product/brand/name/{name}")
    public List<ProductResponse> getProductsByBrandName(@PathVariable String name) {
        return this.adminService.getProductsByBrandName(name).stream().map(this.productMapper::toResponse).toList();
    }

    @PostMapping("/product")
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return this.productMapper.toResponse(this.adminService.createProduct(this.productMapper.fromRequest(request)));
    }

    @PutMapping("/product/{id}")
    public ProductResponse updateProduct(@PathVariable long id, @RequestBody ProductRequest request) {
        return this.productMapper.toResponse(
                this.adminService.updateProduct(id, this.productMapper.fromRequest(request))
        );
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        this.adminService.deleteProduct(id);
    }
}
