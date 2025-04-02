package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

import java.util.List;

/**
 * 최저가 브랜드 DTO.
 */
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
