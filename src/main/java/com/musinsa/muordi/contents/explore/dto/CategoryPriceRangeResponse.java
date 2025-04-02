package com.musinsa.muordi.contents.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.muordi.common.util.NumberWithCommasSerializer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryPriceRangeResponse {
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    private static class Brand {
        @JsonProperty(value="브랜드")
        private String name;

        @JsonProperty(value="가격")
        @JsonSerialize(using = NumberWithCommasSerializer.class)
        private int price;
    }

    @JsonProperty(value="카테고리")
    private String categoryName;

    @JsonProperty(value="최저가")
    private List<Brand> priceLowest;

    @JsonProperty(value="최고가")
    private List<Brand> priceHighest;


    public static CategoryPriceRangeResponse fromDto(CategoryPriceRangeDto dto) {
        CategoryPriceRangeResponse response = new CategoryPriceRangeResponse();
        ItemNamedDto lowest = dto.getMinPriceRecord();
        ItemNamedDto highest = dto.getMaxPriceRecord();

        response.categoryName = lowest.getCategoryName();
        response.priceLowest = List.of(Brand.builder().name(lowest.getBrandName()).price(lowest.getPrice()).build());
        response.priceHighest = List.of(Brand.builder().name(highest.getBrandName()).price(highest.getPrice()).build());

        return response;
    }
}
