package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.exception.RepositoryIntegrityException;
import com.musinsa.muordi.common.exception.RepositoryNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 브랜드 JpaRepository의 래퍼이다.
 */
@RequiredArgsConstructor
@Repository
public class BrandRepositoryJpaWrapper implements BrandRepository {
    private final BrandRepositoryJpa repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brand> findAll() {
        return this.repository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Brand> findById(int id) {
        return this.repository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brand> findByName(String name) {
        return this.repository.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Brand create(Brand brand) {
        return this.repository.save(brand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Brand update(int id, Brand brand) {
        return this.repository.updateById(id, brand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Brand delete(int id) {
        try {
            Brand brand = this.repository.findById(id).orElseThrow(() -> new RepositoryNotExistException("BRAND", id));
            this.repository.delete(brand);
            return brand;
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryIntegrityException("BRAND", id);
        }
    }
}
