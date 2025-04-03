package com.musinsa.muordi.contents.display.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 쇼케이스 수정 요청은 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         categoryId : 전시 카테고리 ID
 *     </li>
 * </ul>
 * 수정 대상 상품 ID는 파라미터로 받는다.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowcaseUpdateRequest {
    @Schema(description = "카테고리 ID", example = "2")
    @NotBlank
    private int categoryId;
}

