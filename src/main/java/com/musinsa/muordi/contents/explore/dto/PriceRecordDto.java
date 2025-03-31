package com.musinsa.muordi.contents.explore.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PriceRecordDto {
    protected int categoryId;
    protected int brandId;
    protected int price;
}
