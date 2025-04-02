package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.exception.RepositoryEntityIntegrityViolation;
import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 상품 JpaRepository의 래퍼이다.
 */
@RequiredArgsConstructor
@Service
public class ProductRepositoryJpaWrapper implements ProductRepository {
    /*
    주의.
    interface Repository, class RepositoryImpl 이런식으로 선언하면 bean 이름 규칙 때문에 JPA repository 구현체와 이름 충돌을 일으켜 순환 참조 오류를 표시함.
     */

    private final ProductRepositoryJpa repository;

    @Override
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Product> findById(long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<Product> findByBrandId(int brandId) {
        return this.repository.findProductByBrand_Id(brandId);
    }

    @Override
    public List<Product> findByBrandName(String brandName) {
        return this.repository.findProductByBrand_Name(brandName);
    }

    @Override
    public Product create(@NonNull Product product) {
        return this.repository.save(product);
    }

    @Override
    public Product update(long id, @NonNull Product product) {
        return this.repository.updateById(id, product);
    }

    @Override
    public Product delete(long id) {
        try {
            Product product = this.findById(id).orElseThrow(() -> new RepositoryEntityNotExistException("PRODUCT", id));
            this.repository.delete(product);
            return product;
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryEntityIntegrityViolation("PRODUCT", "DELETE", id);
        }
    }
}

