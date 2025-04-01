package com.musinsa.muordi.platform.admin.dto;

import lombok.*;

/**
 * 브랜드 요청은 다음의 정보를 포함한다.
 * <ul>
 *     <li>
 *         name : 브랜드 이름.
 *     </li>
 * </ul>
 * 생성 시 브랜드 ID는 존재해선 안되고, 수정 시에는 파라미터로 받는다.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    private String name;
}
