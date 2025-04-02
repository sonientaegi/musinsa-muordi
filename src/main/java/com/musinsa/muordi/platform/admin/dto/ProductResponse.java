package com.musinsa.muordi.platform.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 상품 응답은 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         id : 상품 ID
 *     </li>
 *     <li>
 *         brandId : 브랜드 ID
 *     </li>
 *     <li>
 *         brandName : 브랜드 이름
 *     </li>
 *     <li>
 *         price : 상품 가격
 *     </li>
 * </ul>
 */
@Getter
@AllArgsConstructor
@Builder
public class ProductResponse {
    private final long id;
    private final int brandId;
    private final String brandName;
    private final int price;
}
