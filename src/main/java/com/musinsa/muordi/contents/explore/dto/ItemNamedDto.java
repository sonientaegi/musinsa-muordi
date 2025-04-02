package com.musinsa.muordi.contents.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode
public class ItemNamedDto extends ItemDto {
    protected String categoryName;
    protected String brandName;
}
