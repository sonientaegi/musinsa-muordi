package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.jpa.AtomicUpdateById;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 상품 JpaRepository 확장 명세이다.
 */
@Component
interface ProductRepositoryJpa extends JpaRepository<Product, Long>, AtomicUpdateById<Product, Long> {
    /**
     * SELECT * FROM PRODUCT
     * WHERE brand_id = :brandId
     */
    List<Product> findProductByBrand_Id(int brandId);

    /**
     * SELECT * FROM PRODUCT
     * INNER JOIN BRAND ON brand_id = BRAND.id
     * WHERE BRAND.brand_name = :brandName
     */
    List<Product> findProductByBrand_Name(String brandName);
}

