package com.musinsa.muordi.contents.explore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class CheapestBrandOfCategoryDto {
    private int totalAmount;
    private List<PriceRecordWithNameDto> priceRecords;
}
