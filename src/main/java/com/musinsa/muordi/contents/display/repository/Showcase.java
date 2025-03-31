package com.musinsa.muordi.contents.display.repository;

import com.musinsa.muordi.platform.admin.repository.Product;
import jakarta.persistence.*;
import lombok.*;

//@Getter
//@Setter(AccessLevel.PACKAGE)
//@ToString
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@Entity
//@Table(name = "showcase", indexes = {
//    @Index(name = "showcase_idx_product", columnList = "product_id"),
//    @Index(name = "showcase_idx_category", columnList = "category_id"),
//})
public class Showcase {
//    // 더미 PK. JpaRepository 선언 시 Product entityㄹㄹ ID로 등록하면 Bean 생성이 되지 않는 현상에 대한 해결책을 찾지 못해
//    // 우회로 더미 PK 사용.
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    // 실질적인 PK. 상품 하나는 Showcase에 하나의 전시 카테고리에만 등록할 수 있다.
//    @JoinColumn(name="product_id", nullable = false, unique=true)
//    @ManyToOne(fetch = FetchType.EAGER)
//    Product product;
//
//    @JoinColumn(name="category_id", nullable = false)
//    @ManyToOne(fetch = FetchType.EAGER)
//    Category category;
}
