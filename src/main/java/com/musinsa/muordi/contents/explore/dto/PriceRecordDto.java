package com.musinsa.muordi.contents.explore.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class PriceRecordDto {
    int categoryId;
    int brandId;
    int price;
}
