package com.musinsa.muordi.contents.display.repository;

import com.musinsa.muordi.platform.admin.repository.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name = "muordi_display", indexes = {
    @Index(name = "muordi_display_idx_product", columnList = "product_id"),
    @Index(name = "muordi_display_idx_category", columnList = "category_id"),
})
public class Showcase {
    @Id
    @JoinColumn(name="product_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    Product product;

    @JoinColumn(name="category_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    Category category;
}
