package com.musinsa.muordi.platform.domain.product;

import com.musinsa.muordi.platform.domain.brand.Brand;
import com.musinsa.muordi.utils.jpa.AtomicUpdateById;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceException;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 상품 DML을 정의한다.
 */
@Component
interface ProductRepositoryJpa extends JpaRepository<Product, Long>, AtomicUpdateById<Product, Long> {
    /**
     * 브랜드 명칭으로 상품을 조회한다.
     * @param brandName 조회 할 상품의 브랜드 명칭.
     * @return 발견한 브랜드 리스트, 또는 빈 리스트.
     */
    List<Product> findProductByBrand_Name(String brandName);

    /**
     * 브랜드 식별자로 상품을 조회한다.
     * @param id 조회할 상품의 브랜드 식별자.
     * @return 발견한 브랜드 리스트, 또는 빈 리스트.
     */
    List<Product> findProductByBrand_Id(int id);
}

