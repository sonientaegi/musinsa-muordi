package com.musinsa.muordi.contents.explore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.NumberFormat;

/**
 * 카테고리, 브랜드, 가격을 포함하는 전시상품
 */
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ItemDto {
    @JsonIgnore
    protected int categoryId;

    @JsonIgnore
    protected int brandId;

    @JsonProperty(value="가격")
    protected int price;
}
