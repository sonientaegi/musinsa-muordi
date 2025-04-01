package com.musinsa.muordi.contents.display.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 카테고리 DML을 정의한다. 모든 결과는 공통적으로 display_sequence 오름차순으로 정렬한다.
 */
public interface CategoryRepositoryJpa extends JpaRepository<Category, Integer> {
    /**
     * SELECT * FROM CATEGORY
     * ORDER BY display_sequence asc
     */
    List<Category> findAllByOrderByDisplaySequenceAsc();


    /**
     * SELECT * FROM CATEGORY
     * WHERE name = :name
     * ORDER BY display_sequence asc
     */
    List<Category> findByNameOrderByDisplaySequenceAsc(String name);
}
