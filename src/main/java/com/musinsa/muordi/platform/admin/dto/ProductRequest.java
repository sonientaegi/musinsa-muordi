package com.musinsa.muordi.platform.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 상품 요청은 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         brandId : 브랜드 ID
 *     </li>
 *     <li>
 *         price : 상품 가격
 *     </li>
 * </ul>
 * 생성 시 상품 ID는 존재해선 안되고, 수정 시에는 파라미터로 받는다. 브랜드 ID는 필수이다.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @Schema(description = "브랜드 ID", example = "47")
    @NotBlank
    private int brandId;

    @Schema(description = "상품가격", example = "43900")
    private int price;
}
