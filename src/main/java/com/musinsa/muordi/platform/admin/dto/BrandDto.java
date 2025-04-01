package com.musinsa.muordi.platform.admin.dto;

import com.musinsa.muordi.platform.admin.repository.Brand;
import lombok.*;

import java.util.List;

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
 * 식별자는 읽기 전용이다. Repository로 읽어들인 경우에 한해 값을 가진다.
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
}
