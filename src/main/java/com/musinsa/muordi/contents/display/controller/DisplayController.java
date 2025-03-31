package com.musinsa.muordi.contents.display.controller;

import com.musinsa.muordi.contents.display.dto.CategoryDto;
import com.musinsa.muordi.contents.display.dto.ShowcaseCreateRequest;
import com.musinsa.muordi.contents.display.dto.ShowcaseDto;
import com.musinsa.muordi.contents.display.dto.ShowcaseUpdateRequest;
import com.musinsa.muordi.contents.display.service.DisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/display/v1")
@RequiredArgsConstructor
@RestController
public class DisplayController {
    // 전시 서비스
    private final DisplayService displayService;

    @GetMapping("/category")
    public List<CategoryDto> getCategories() {
        return this.displayService.getCategories();
    }

    @GetMapping("/showcase")
    public List<ShowcaseDto> getShowcases() {
        return this.displayService.getShowcases();
    }

    @GetMapping("/showcase/{id}")
    public Optional<ShowcaseDto> getShowcaseById(@PathVariable long id) {
        return this.displayService.getShowcase(id);
    }

    @GetMapping("/showcase/category/{id}")
    public List<ShowcaseDto> getShowcasesByCategory(@PathVariable int id) {
        return this.displayService.getShowcasesByCategoryId(id);
    }

    @GetMapping("/showcase/category/name/{name}")
    public List<ShowcaseDto> getShowcasesByCategory(@PathVariable String name) {
        return this.displayService.getShowcasesByCategoryName(name);
    }

    @GetMapping("/showcase/brand/{id}")
    public List<ShowcaseDto> getShowcasesByBrand(@PathVariable int id) {
        return this.displayService.getShowcasesByBrandId(id);
    }

    @GetMapping("/showcase/brand/name/{name}")
    public List<ShowcaseDto> getShowcasesByBrand(@PathVariable String name) {
        return this.displayService.getShowcasesByBrandName(name);
    }

    @PostMapping("/showcase")
    public ShowcaseDto newShowcase(@RequestBody ShowcaseCreateRequest request) {
        return this.displayService.createShowcase(request.getProductId(), request.getCategoryId());
    }

    @PutMapping("/showcase/{id}")
    public Optional<ShowcaseDto> updateShowcase(@PathVariable long id, @RequestBody ShowcaseUpdateRequest request) {
        return this.displayService.updateShowcase(id, request.getCategoryId());
    }

    @DeleteMapping("/showcase/{id}")
    public void deleteShowcase(@PathVariable long id) {
        this.displayService.deleteShowcase(id);
    }
}
