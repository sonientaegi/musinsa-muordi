package com.musinsa.muordi.platform.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musinsa.muordi.platform.domain.brand.Brand;
import com.musinsa.muordi.utils.jpa.EntityUpdate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
 * PRODUCT는 상품을 판매하는 브랜드와 가격정보를 갖는다. 등록한 상품의 가격은 변경할 수 있다.
 * 등록한 상품의 브랜드는 변경이 불가하며 이 경우 상품을 새로 등록해야한다.
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

    @Setter(AccessLevel.PACKAGE)
    @JsonIgnore
    @JoinColumn(name="brand_id", nullable = false, updatable = false)
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
        this.setPrice(src.getPrice());
    }

    @Override
    public Object clone() {
        return new Product(this.getId(), this.getBrand(), this.getPrice());
    }
}
