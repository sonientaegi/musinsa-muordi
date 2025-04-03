package com.musinsa.muordi.contents.explore.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 최저가 브랜드 DTO.
 */
@Hidden
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
