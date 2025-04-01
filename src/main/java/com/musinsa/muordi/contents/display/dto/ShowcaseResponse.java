package com.musinsa.muordi.contents.display.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 쇼케이스 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         productId : 상품 ID
 *     </li>
 *     <li>
 *         price : 상품 가격
 *     </li>
 *     <li>
 *         categoryId : 전시 카테고리 ID
 *     </li>
 *     <li>
 *         categoryName : 전시 카테고리 이름
 *     </li>
 *     <li>
 *         brandId : 브랜드 ID
 *     </li>
 *     <li>
 *         brandName : 브랜드 이름
 *     </li>
 * </ul>
 */
@Getter
@AllArgsConstructor
@Builder
public class ShowcaseResponse {
    private final Long productId;
    private final Integer price;
    private final Integer categoryId;
    private final String categoryName;
    private final Integer brandId;
    private final String brandName;
}
