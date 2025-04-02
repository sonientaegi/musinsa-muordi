package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

import java.util.List;

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
