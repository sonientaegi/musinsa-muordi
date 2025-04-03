package com.musinsa.muordi.contents.explore.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.util.List;

/**
 * 카테고리별 최저가 브랜드 DTO.
 */
@Hidden
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
