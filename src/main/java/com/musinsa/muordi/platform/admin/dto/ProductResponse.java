package com.musinsa.muordi.platform.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "상품 ID", example = "74")
    private final long id;

    @Schema(description = "브랜드 ID", example = "47")
    private final int brandId;

    @Schema(description = "브랜드 이름", example = "소년 스튜디오")
    private final String brandName;

    @Schema(description = "가격", example = "43900")
    private final int price;
}
