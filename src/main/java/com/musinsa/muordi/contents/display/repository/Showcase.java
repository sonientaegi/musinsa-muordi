package com.musinsa.muordi.contents.display.repository;

import com.musinsa.muordi.common.jpa.EntityUpdate;
import com.musinsa.muordi.platform.admin.repository.Product;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * SHOWCASE entity를 정의한다.
 * <ul>
 *     <li>PRODUCT {@link Showcase#product}(UK) 상품 ID</li>
 *     <li>CATEGORY {@link Showcase#category}(FK) 카테고리 ID</li>
 * </ul>
 * 인덱스 정보
 * <ul>
 *     <li>{@link Showcase#product} 브랜드 조건 검색용</li>
 *     <li>{@link Showcase#category} 브랜드 조건 검색용</li>
 * </ul>
 * SHOWCASE 의 PK 인 ID는 레코드 저장을 위한 용도로만 사용하며, 비지니스로직에서는 사용하지 않는다.
 * UniqueKey 인 상품 ID가 쇼케이스의 실질적인 PK 역할을 한다. 하나의 상품은 오직 하나의 전시 카테고리에만 등록할 수 있다.
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name = "showcase", indexes = {
    @Index(name = "showcase_idx_product", columnList = "product_id"),
    @Index(name = "showcase_idx_category", columnList = "category_id"),
})
public class Showcase implements Serializable, EntityUpdate<Showcase> {
    // 더미 PK. JpaRepository 선언 시 Product entityㄹㄹ ID로 등록하면 Bean 생성이 되지 않는 현상에 대한 해결책을 찾지 못해
    // 우회로 더미 PK 사용. 생성시에는 null값.
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 실질적인 PK. 상품 하나는 Showcase에 하나의 전시 카테고리에만 등록할 수 있다.
    @JoinColumn(name="product_id", nullable = false, unique=true)
    @ManyToOne(fetch = FetchType.EAGER)
    Product product;

    @JoinColumn(name="category_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    Category category;

    @Builder
    public Showcase(Product product, Category category) {
        this.product = product;
        this.category = category;
    }

    @Override
    public void updateFrom(Showcase src) {
        this.setCategory(src.category);
    }
}
