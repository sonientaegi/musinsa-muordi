package com.musinsa.muordi.contents.explore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.muordi.common.util.NumberWithCommasSerializer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryBrandCheapestReponse {
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

    public static CategoryBrandCheapestReponse fromDto(CategoryBrandCheapestDto dto) {
        CategoryBrandCheapestReponse response = new CategoryBrandCheapestReponse();
        response.totalAmount = dto.getTotalAmount();
        response.products = new ArrayList<>();
        dto.getPriceRecords().forEach(record -> {
            response.products.add(Product.builder().categoryName(record.getCategoryName()).brandName(record.getBrandName()).price(record.getPrice()).build());
        });

        return response;
    }
}
