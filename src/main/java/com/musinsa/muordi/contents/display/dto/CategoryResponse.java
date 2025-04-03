package com.musinsa.muordi.contents.display.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 카테고리 응답은 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         id : 카테고리 ID.
 *     </li>
 *     <li>
 *         name : 카테고리 이름.
 *     </li>
 *     <li>
 *         displaySequence : 카테고리 정렬 순서.
 *     </li>
 * </ul>
 */
@Getter
@AllArgsConstructor
@Builder
public class CategoryResponse {
    @Schema(description = "전시 카테고리 ID", example = "1")
    private Integer id;

    @Schema(description = "전시 카테고리 이름", example = "상의")
    private String name;

    @Schema(description = "전시 순서", example = "1")
    private int displaySequence;

}
