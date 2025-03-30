package com.musinsa.muordi.platform.admin.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musinsa.muordi.common.jpa.EntityUpdate;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * PRODUCT entity를 정의한다.
 * <ul>
 *     <li>Long {@link Product#id}(PK) 상품 식별자</li>
 *     <li>BRAND {@link Product#brand}(FK) 브랜드 식별자</li>
 *     <li>Integer price</li>
 * </ul>
 * 인덱스 정보
 * <ul>
 *     <li>{@link Product#brand} 브랜드 조건 검색용</li>
 * </ul>
 * PRODUCT는 상품을 판매하는 브랜드와 가격정보를 갖는다. 등록한 상품의 가격과 브랜드는 수정이 가능하다.
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name = "product", indexes = {
        @Index(name = "product_idx_brand", columnList = "brand_id"),
})
public class Product implements Serializable, EntityUpdate<Product> {
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn(name="brand_id", nullable = false, updatable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;
    private int price;

    @Builder
    public Product(Brand brand, int price) {
        this.brand = brand;
        this.price = price;
    }

    @Override
    public void updateFrom(Product src) {
        this.setBrand(src.getBrand());
        this.setPrice(src.getPrice());
    }

    @Override
    public Product clone() {
        return new Product(this.getId(), this.getBrand().clone(), this.getPrice());
    }
}
