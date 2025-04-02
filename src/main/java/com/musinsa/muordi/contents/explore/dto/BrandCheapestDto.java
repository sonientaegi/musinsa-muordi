package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class BrandCheapestDto {
    private int brandId;
    private String brandName;
    private int totalAmount;
    List<ItemNamedDto> priceRecords;
}
