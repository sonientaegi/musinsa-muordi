package com.musinsa.muordi.platform.domain.category;


import jakarta.persistence.*;
import lombok.*;

/**
 * CATEGORY entity를 정의한다.
 * <ul>
 *     <li>Integer id (PK)</li>
 *     <li>String  name </li>
 *     <li>Integer display_sequence</li>
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
@Table(name = "category", indexes = {
        @Index(name = "category_idx_name", columnList = "name"),
})
public class Category {
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private Integer display_sequence;
}
