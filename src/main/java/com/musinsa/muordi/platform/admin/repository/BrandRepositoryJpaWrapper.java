package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.exception.RepositoryEntityIntegrityViolation;
import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 브랜드 JpaRepository 의 래퍼이다.
 */
@RequiredArgsConstructor
@Repository
public class BrandRepositoryJpaWrapper implements BrandRepository {
    private final BrandRepositoryJpa repository;

    @Override
    public Optional<Brand> findById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public List<Brand> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public List<Brand> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Brand create(Brand brand) {
        return this.repository.save(brand);
    }

    @Override
    public Brand update(int id, Brand brand) {
        return this.repository.updateById(id, brand);
    }

    @Override
    public Brand delete(int id) {
        try {
            Brand brand = this.repository.findById(id).orElseThrow(() -> new RepositoryEntityNotExistException("BRAND", id));
            this.repository.delete(brand);
            return brand;
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryEntityIntegrityViolation("BRAND", "DELETE", id);
        }
    }
}
