package com.musinsa.muordi.contents.display.dto;

import lombok.*;

/**
 * 카테고리 DTO는 다음의 정보를 제공한다.
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
 * 카테고리는 읽기 전용이다.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;
    private int displaySequence;

    @Override
    protected Object clone() {
        return new CategoryDto(this.id, this.name, this.displaySequence);
    }
}
