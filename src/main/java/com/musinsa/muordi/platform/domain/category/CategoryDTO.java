package com.musinsa.muordi.platform.domain.category;

import lombok.*;

/**
 * 카테고리 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         id : 카테고리 식별자.
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
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CategoryDTO {
    @Setter(AccessLevel.PACKAGE)
    private Integer id;
    private String name;
    private int displaySequence;
}
