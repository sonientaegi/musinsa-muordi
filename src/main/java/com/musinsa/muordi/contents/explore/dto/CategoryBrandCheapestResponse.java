package com.musinsa.muordi.contents.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.muordi.common.util.NumberWithCommasSerializer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 카테고리 별 최저가 브랜드 응답.
 */
@Getter
@NoArgsConstructor
public class CategoryBrandCheapestResponse {
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    private static class Product {
        @JsonProperty(value = "카테고리")
        private String categoryName;

        @JsonProperty(value = "브랜드")
        private String brandName;

        @JsonProperty(value = "가격")
        @JsonSerialize(using = NumberWithCommasSerializer.class)
        private int price;
    }

    @JsonProperty(value = "총액")
    @JsonSerialize(using = NumberWithCommasSerializer.class)
    private int totalAmount;

    @JsonProperty(value = "최저가")
    private List<Product> products;

    public static CategoryBrandCheapestResponse fromDto(CategoryBrandCheapestDto dto) {
        CategoryBrandCheapestResponse response = new CategoryBrandCheapestResponse();
        response.totalAmount = dto.getTotalAmount();
        response.products = new ArrayList<>();
        dto.getPriceRecords().forEach(record -> {
            response.products.add(Product.builder().categoryName(record.getCategoryName()).brandName(record.getBrandName()).price(record.getPrice()).build());
        });

        return response;
    }
}
