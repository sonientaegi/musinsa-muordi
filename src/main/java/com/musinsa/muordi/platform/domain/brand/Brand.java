package com.musinsa.muordi.platform.domain.brand;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musinsa.muordi.platform.domain.product.Product;
import com.musinsa.muordi.utils.jpa.EntityUpdate;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import java.util.List;

/**
 * BRAND entity를 정의한다.
 * <ul>
 *     <li>Integer {@link Brand#id}(PK) 브랜드 식별자.</li>
 *     <li>Products {@link Brand#products}(FK) 브랜드에서 판매중인 상품{@link Product} 리스트. </li>
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
public class Brand implements Serializable, EntityUpdate<Brand> {
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ToString.Exclude
    @OneToMany(
            // Brand : Product = 1:N. 브랜드 삭제시 연관 상품도 모두 삭제 하도록 일관성을 지정한다.
            mappedBy = "brand",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private List<Product> products;

    /**
     * 신규 브랜드 생성에 사용하는 생성자. 브랜드의 이름을 지정한 객체를 생성한 다음
     * {@link BrandRepository#save(Object)} 또는 {@link BrandRepository#saveAll(Iterable)}를 호출하여 새로운 브랜드 레코드를 생성한다.
     * @param name 브랜드 명칭
     */
    @Builder
    public Brand(String name) {
        this.name = name;
    }

    @Override
    public void updateFrom(Brand src) {
        this.setName(src.getName());
    }

    @Override
    public Brand clone() {
        // 역참조항목은 복제하지 않는다. 순환참조에 의한 무한루프 발생.
        Brand brand = new Brand(this.getId(), this.getName(), this.getProducts());
        return brand;
    }
}
