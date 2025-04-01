package com.musinsa.muordi.contents.display.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 카테고리 JpaRepository의 래퍼이다.
 */
@RequiredArgsConstructor
@Service
public class CategoryRepositoryJpaWrapper implements CategoryRepository {
    private final CategoryRepositoryJpa repository;

    public List<Category> findAll() {
        return this.repository.findAllByOrderByDisplaySequenceAsc();
    }

    @Override
    public List<Category> findByName(String name) {
        return this.repository.findByNameOrderByDisplaySequenceAsc(name);
    }

    @Override
    public Optional<Category> findById(int id) {
        return this.repository.findById(id);
    }
}
