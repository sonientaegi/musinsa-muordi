package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PriceRangeOfCategoryDto {
    private int categoryId;
    private String categoryName;
    private PriceRecordWithNameDto maxPriceRecord;
    private PriceRecordWithNameDto minPriceRecord;
}
