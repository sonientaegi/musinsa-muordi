package com.musinsa.muordi.contents.display.controller;

import com.musinsa.muordi.contents.display.dto.CategoryDto;
import com.musinsa.muordi.contents.display.service.DisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
