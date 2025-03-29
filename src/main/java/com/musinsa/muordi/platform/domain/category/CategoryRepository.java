package com.musinsa.muordi.platform.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 카테고리 DML을 정의한다. 모든 결과는 공통적으로 display_sequence 오름차순으로 정렬한다.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    /**
     * {@link CategoryRepository#findAll()}의 원형이다.
     */
    List<Category> findAllByOrderByDisplaySequenceAsc();

    /**
     * 모든 결과를 display_sequence 오름차순으로 정렬하여 반환한다.
     * @return 발견시 모든 카테고리 리스트, 또는 빈 리스트.
     * @see CategoryRepository#findAllByOrderByDisplaySequenceAsc()
     */
    default List<Category> findAll() {
        return this.findAllByOrderByDisplaySequenceAsc();
    }

    /**
     * {@link CategoryRepository#findByName(String)}의 원형이다.
     * @param name
     * @return
     */
    List<Category> findByNameOrderByDisplaySequenceAsc(String name);

    /**
     * 모든 결과를 display_sequence 오름차순으로 정렬하여 반환한다.
     * @param name 카테고리 이름
     * @return 발견 시 카테고리 리스트, 또는 빈 리스트
     * @see CategoryRepository#findByNameOrderByDisplaySequenceAsc(String)
     */
    default List<Category>findByName(String name){
        return this.findByNameOrderByDisplaySequenceAsc(name);
    }
}
