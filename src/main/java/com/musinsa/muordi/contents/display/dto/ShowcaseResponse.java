package com.musinsa.muordi.contents.display.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "전시 상품 ID", example = "74")
    private final Long productId;

    @Schema(description = "상품 가격", example = "43900")
    private final Integer price;

    @Schema(description = "전시 카테고리 ID", example = "1")
    private final Integer categoryId;

    @Schema(description = "전시 카테고리 이름", example = "상의")
    private final String categoryName;

    @Schema(description = "브랜드 ID", example = "47")
    private final Integer brandId;

    @Schema(description = "브랜드 이름", example = "소년 스튜디오")
    private final String brandName;
}
