package com.musinsa.muordi.platform.admin.repository;


import com.musinsa.muordi.common.jpa.EntityUpdate;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * BRAND entity를 정의한다.
 * <ul>
 *     <li>Integer {@link Brand#id}(PK) 브랜드 ID.</li>
 *     <li>Products {@link Brand#products} 브랜드에서 판매중인 상품{@link Product} 역참조 리스트. </li>
 *     <li>String  {@link Brand#name} 브랜드 이름</li>
 * </ul>
 * 인덱스 정보
 * <ul>
 *     <li>{@link Brand#name} 브랜드 이름 검색</li>
 * </ul>
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name = "brand", indexes = {
        // 브랜드 이름 조회.
        @Index(name = "brand_idx_name", columnList = "name"),
})
public class Brand implements Serializable, EntityUpdate<Brand> {
    @Id
    @Setter(AccessLevel.PACKAGE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Setter(AccessLevel.PACKAGE)
    @ToString.Exclude
    @OneToMany(
            // Brand : Product = 1:N. 브랜드 삭제시 연관 상품도 모두 삭제 하도록 일관성을 지정한다.
            mappedBy = "brand",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private List<Product> products;

//    public Brand(String name, List<Product> products) {
//        this.name = name;
//        this.products = products;
//    }

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
