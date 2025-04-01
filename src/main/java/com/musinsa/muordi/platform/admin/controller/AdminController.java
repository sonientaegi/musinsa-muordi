package com.musinsa.muordi.platform.admin.controller;

import com.musinsa.muordi.platform.admin.dto.BrandDto;
import com.musinsa.muordi.platform.admin.dto.BrandDtoMapper;
import com.musinsa.muordi.platform.admin.dto.BrandRequest;
import com.musinsa.muordi.platform.admin.dto.ProductDto;
import com.musinsa.muordi.platform.admin.dto.BrandResponse;
import com.musinsa.muordi.platform.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/admin/v1")
@RequiredArgsConstructor
@RestController
public class AdminController {
    // 어드민 서비스
    private final AdminService adminService;

    // request/response - DTO 맵퍼
    private final BrandDtoMapper brandMapper = BrandDtoMapper.instance;

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
        BrandDto updatedBrandDto = this.adminService.updateBrand(id, brandDto);
        if (updatedBrandDto == null) {
            throw new RuntimeException("fail");
        } else {
            return this.brandMapper.toResponse(updatedBrandDto);
        }
    }

    @DeleteMapping("/brand/{id}")
    public void deleteById(@PathVariable int id) {
        this.adminService.deleteBrand(id);
    }

    @GetMapping("/product")
    public List<ProductDto> getProducts() {
        return this.adminService.getProducts();
    }

    @GetMapping("/product/{id}")
    public ProductDto getProduct(@PathVariable int id) {
        return this.adminService.getProduct(id).orElse(null);
    }

    @GetMapping("/product/brand/{id}")
    public List<ProductDto> getProductsByBrand(@PathVariable int id) {
        return this.adminService.getProductsByBrandId(id);
    }

    @GetMapping("/product/brand/name/{name}")
    public List<ProductDto> getProductsByBrandName(@PathVariable String name) {
        return this.adminService.getProductsByBrandName(name);
    }

    @PostMapping("/product")
    public ProductDto newProduct(@RequestBody ProductDto productDto) {
        return this.adminService.newProduct(productDto);
    }

    @PutMapping("/product/{id}")
    public ProductDto updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        return this.adminService.updateProduct(id, productDto).orElse(null);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        this.adminService.deleteProduct(id);
    }

}
