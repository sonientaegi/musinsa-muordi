package com.musinsa.muordi.contents.explore.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 카테고리, 브랜드, 가격을 포함하는 전시상품 + 이름.
 */
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode
public class ItemNamedDto extends ItemDto {
    protected String categoryName;
    protected String brandName;
}
