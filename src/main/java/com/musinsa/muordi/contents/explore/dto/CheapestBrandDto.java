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
public class CheapestBrandDto {
    private int brandId;
    private String brandName;
    private int totalAmount;
    List<PriceRecordWithNameDto> priceRecords;
}
