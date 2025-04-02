package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

import java.util.List;

/**
 * 카테고리별 최저가 브랜드 DTO.
 */
@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CategoryBrandCheapestDto {
    private int totalAmount;
    private List<ItemNamedDto> priceRecords;
}
