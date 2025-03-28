package com.musinsa.muordi.platform.domain.brand;

import lombok.*;

/**
 * 브랜드 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         id : 브랜드 식별자.
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
public class BrandDTO {
    @Setter(AccessLevel.PACKAGE)
    private Integer id;
    private String name;
}
