package com.musinsa.muordi.contents.explore.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
public class PriceRecordWithNameDto extends PriceRecordDto {
    protected String categoryName;
    protected String brandName;
}
