package com.musinsa.muordi.contents.explore.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode
public class PriceRecordWithNameDto extends PriceRecordDto {
    protected String categoryName;
    protected String brandName;
}
