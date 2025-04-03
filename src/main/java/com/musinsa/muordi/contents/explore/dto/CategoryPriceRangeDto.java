package com.musinsa.muordi.contents.explore.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * 최고가, 최저가 DTO.
 */
@Hidden
@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPriceRangeDto {
    private int categoryId;
    private String categoryName;
    private ItemNamedDto maxPriceRecord;
    private ItemNamedDto minPriceRecord;
}
