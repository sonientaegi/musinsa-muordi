package com.musinsa.muordi.platform.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 브랜드 응답은 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         id : 브랜드 ID.
 *     </li>
 *     <li>
 *         name : 브랜드 이름.
 *     </li>
 * </ul>
 */
@Getter
@AllArgsConstructor
@Builder
public class BrandResponse {
    private final int id;
    private final String name;
}
