package com.musinsa.muordi.platform.domain.brand;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * 브랜드 entity를 정의한다.
 * <ul>
 *     <li>Integer {@link Brand#id}(PK) 브랜드 식별자.</li>
 *     <li>String  {@link Brand#name} 브랜드 명칭</li>
 * </ul>
 * 인덱스 정보
 * <ul>
 *     <li>{@link Brand#name}</li>
 * </ul>
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name = "brand", indexes = {
        @Index(name = "brand_idx_name", columnList = "name"),
})
public class Brand implements Serializable {
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Override
    protected Object clone() {
        return new Brand(this.getId(), this.getName());
    }
}
