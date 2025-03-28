package com.musinsa.muordi.platform.domain.brand;


import jakarta.persistence.*;
import lombok.*;

/**
 * BRAND entity를 정의한다.
 * <ul>
 *     <li>Integer id (PK)</li>
 *     <li>String  name </li>
 * </ul>
 * 인덱스 정보
 * <ul>
 *     <li>name</li>
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
public class Brand {
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
