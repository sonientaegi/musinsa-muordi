package com.musinsa.muordi.platform.admin.dto;

import lombok.*;

/**
 * 브랜드 DTO는 다음의 정보를 제공한다.
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
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BrandDto {
    private Integer id;
    private String name;

    @Builder
    public BrandDto(String name) {
        this.name = name;
    }

    @Override
    public BrandDto clone() {
        return new BrandDto(this.id, this.name);
    }
}
