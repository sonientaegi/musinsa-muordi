package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

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
