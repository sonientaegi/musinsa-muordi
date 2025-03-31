package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CheapestBrandOfCategoryDto {
    private int totalAmount;
    private List<PriceRecordWithNameDto> priceRecords;
}
