package com.musinsa.muordi.contents.display.dto;

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
    private Integer id;
    private String name;
    private int displaySequence;

}
