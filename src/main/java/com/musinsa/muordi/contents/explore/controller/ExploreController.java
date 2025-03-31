package com.musinsa.muordi.contents.explore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/explore/v1")
@RequiredArgsConstructor
@RestController
public class ExploreController {

    @GetMapping("/coordi/affordable")
    public void getAffordableCoordi() {}

    @GetMapping("/coordi/affordable/brand")
    public void getAffordableBrand() {}

    @GetMapping("/coordi/category/{name}/price/range")
    public void getCategoryPriceRange(@PathVariable String name) {}
}
