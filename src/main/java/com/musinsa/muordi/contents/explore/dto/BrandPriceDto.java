package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandPriceDto {
    private String brandName;
    private int price;
}
