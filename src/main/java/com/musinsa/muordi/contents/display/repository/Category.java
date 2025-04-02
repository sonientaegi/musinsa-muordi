package com.musinsa.muordi.contents.display.repository;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * CATEGORY entity를 정의한다.
 * <ul>
 *     <li>Integer {@link Category#id}(PK) ID.</li>
 *     <li>String  {@link Category#name} 카테고리 이름.</li>
 *     <li>Integer {@link Category#displaySequence} 전시 순서.</li>
 * </ul>
 * 인덱스 정보
 * <ul>
 *     <li>{@link Category#name} 카테고리 이름 검색</li>
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
        // 카테고리 이름 조회.
        @Index(name = "category_idx_name", columnList = "name"),
})
public class Category implements Serializable {
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    /**
     * 전시 순서는 테이블 내 유일값이며, display_sequence 컬럼에 맵핑한다.
     */
    @Column(name="display_sequence", unique = true)
    private int displaySequence;
}
