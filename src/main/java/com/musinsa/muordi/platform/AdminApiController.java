package com.musinsa.muordi.platform;

import com.musinsa.muordi.platform.domain.BrandDto;
import com.musinsa.muordi.platform.domain.CategoryDto;
import com.musinsa.muordi.platform.domain.DomainService;
import com.musinsa.muordi.platform.domain.brand.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/v1")
@RequiredArgsConstructor
@RestController
public class AdminApiController {
    // 도메인 서비스
    private final DomainService domainService;

    @GetMapping("/category")
    public List<String> getCategories() {
        List<CategoryDto> categories = this.domainService.getCategories();
        return categories.stream().map(CategoryDto::getName).toList();
    }

    @PostMapping("/brand")
    public BrandDto newBrand(@RequestBody BrandDto brandDto) {
        BrandDto newBrandDto = this.domainService.newBrand(brandDto);
        if (newBrandDto == null) {
            throw new RuntimeException("fail");
        } else {
            return newBrandDto;
        }
    }

    @GetMapping("/brand")
    public List<BrandDto> getBrand() {
        return this.domainService.getBrands();
    }

    @GetMapping("/brand/{id}")
    public BrandDto getBrand(@PathVariable int id) {
        return this.domainService.getBrand(id).orElse(null);
    }

    @PutMapping("/brand/{id}")
    public BrandDto updateBrand(@PathVariable int id, @RequestBody BrandDto brandDto) {
        BrandDto updatedBrandDto = this.domainService.updateBrand(id, brandDto).orElse(null);
        if (updatedBrandDto == null) {
            throw new RuntimeException("fail");
        } else {
            return updatedBrandDto;
        }
    }

    @DeleteMapping("/brand/{id}")
    public void deleteById(@PathVariable int id) {
        this.domainService.deleteBrand(id);
    }
}
