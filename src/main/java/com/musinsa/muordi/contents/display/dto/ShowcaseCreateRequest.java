package com.musinsa.muordi.contents.display.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 쇼케이스 등록 요청은 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         productId : 상품 ID
 *     </li>
 *     <li>
 *         categoryId : 전시 카테고리 ID
 *     </li>
 * </ul>
 * 하나의 상품은 하나의 전시 카테고리에만 맵핑이 가능하다.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowcaseCreateRequest {
    @Schema(description = "전시 상품 ID", example = "47")
    @NotBlank
    private long productId;

    @Schema(description = "전시 카테고리 ID", example = "1")
    @NotBlank
    private int categoryId;
}

