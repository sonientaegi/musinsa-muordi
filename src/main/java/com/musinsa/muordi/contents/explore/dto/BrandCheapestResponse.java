package com.musinsa.muordi.contents.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.muordi.common.util.NumberWithCommasSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 최저가 브랜드 응답.
 */
@Getter
@NoArgsConstructor
public class BrandCheapestResponse {
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    private static class Category {
        @JsonProperty(value = "카테고리")
        @Schema(example = "상의")
        private String name;

        @JsonProperty(value = "가격")
        @Schema(example = "10,100")
        @JsonSerialize(using = NumberWithCommasSerializer.class)
        private int price;
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    private static class ProductsOfBrand {
        @Schema(description = "브랜드", example = "D")
        @JsonProperty(value="브랜드")
        private String name;

        @Schema(description = "카테고리", example = "상의")
        @JsonProperty(value="카테고리")
        private List<Category> categories;

        @JsonProperty(value="총액")
        @JsonSerialize(using = NumberWithCommasSerializer.class)
        @Schema(description = "가격", example = "10,100")
        private int totalAmount;
    }

    @JsonProperty(value="최저가")
    private ProductsOfBrand cheapest;

    public static BrandCheapestResponse fromDto(BrandCheapestDto dto) {
        List<Category> categories = new ArrayList<>();
        dto.getPriceRecords().forEach(record -> {
            categories.add(Category.builder().name(record.getCategoryName()).price(record.getPrice()).build());
        });

        BrandCheapestResponse response = new BrandCheapestResponse();
        response.cheapest = ProductsOfBrand.builder().name(dto.getBrandName()).totalAmount(dto.getTotalAmount()).categories(categories).build();

        return response;
    }
}
