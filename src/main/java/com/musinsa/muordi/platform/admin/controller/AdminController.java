package com.musinsa.muordi.platform.admin.controller;

import com.musinsa.muordi.platform.admin.dto.service.BrandDto;
import com.musinsa.muordi.platform.admin.dto.service.ProductDto;
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

    @PostMapping("/brand")
    public BrandDto newBrand(@RequestBody BrandDto brandDto) {
        BrandDto newBrandDto = this.adminService.newBrand(brandDto);
        if (newBrandDto == null) {
            throw new RuntimeException("fail");
        } else {
            return newBrandDto;
        }
    }

    @GetMapping("/brand")
    public List<BrandDto> getBrand() {
        return this.adminService.getBrands();
    }

    @GetMapping("/brand/{id}")
    public BrandDto getBrand(@PathVariable int id) {
        return this.adminService.getBrand(id).orElse(null);
    }

    @PutMapping("/brand/{id}")
    public BrandDto updateBrand(@PathVariable int id, @RequestBody BrandDto brandDto) {
        BrandDto updatedBrandDto = this.adminService.updateBrand(id, brandDto).orElse(null);
        if (updatedBrandDto == null) {
            throw new RuntimeException("fail");
        } else {
            return updatedBrandDto;
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
