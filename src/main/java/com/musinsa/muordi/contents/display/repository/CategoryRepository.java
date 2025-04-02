package com.musinsa.muordi.contents.display.repository;

import java.util.List;
import java.util.Optional;


/**
 * 카테고리 repository 의 DML을 명세한 인터페이스이다.
 * 모든 결과는 공통적으로 전시 순서(display_sequence) 오름차순으로 정렬한다.
 */
public interface CategoryRepository {
    /**
     * 모든 카테고리를 전시 순서 오름차순으로 반환한다.
     * @return 오름차순으로 정렬한 모든 카테고리 entity 리스트.
     * @see CategoryRepositoryJpa#findAllByOrderByDisplaySequenceAsc()
     */
    List<Category> findAll();

    /**
     * 이름으로 카테고리를 조회한다.
     * @param name 카테고리 이름. 같은 이름을 가진 카테고리가 여러개있으면 모두 반환한다.
     * @return 카테고리 entity 리스트.
     */
    List<Category> findByName(String name);

    /**
     * ID로 카테고리를 조회한다. ID는 카테고리의 유일한 식별자이며 두 개 이상의 같은 값은 존재하지 않는다.
     * @param id 카테고리 ID
     * @return Optional로 감싼 카테고리 entity, 미발견 시 빈 Optional을 반환한다. null 일 수 없다.
     */
    Optional<Category> findById(int id);

}
