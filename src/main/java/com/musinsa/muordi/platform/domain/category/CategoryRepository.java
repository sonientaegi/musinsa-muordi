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
     * 모든 결과를 반환한다.
     * @return 모든 카테고리 레코드의 DTO를 display_sequence 오름차순으로 정렬하여 반환하거나, 미발견시 빈 리스트를 반환한다.
     */
    @Query(value = """
        select new com.musinsa.muordi.platform.domain.category.CategoryDTO(category.id, category.name, category.display_sequence)
            from Category category
            order by category.display_sequence asc
    """)
    List<CategoryDTO> all();

    /**
     * 카테고리를 단건 조회한다.
     * @param id 조회할 카테고리의 식별자
     * @return 발견한 카테고리 레코드의 DTO를 반환하거나, null을 반환한다.
     */
    @Query(value = """
        select new com.musinsa.muordi.platform.domain.category.CategoryDTO(category.id, category.name, category.display_sequence)
            from Category category 
            where category.id = :id
    """)
    CategoryDTO byId(@Param("id") Integer id);

    /**
     * 카테고리 명칭으로 카테고리를 조회한다.
     * @param name 카테고리 명칭
     * @return 발견한 모든 카테고리 레코드의 DTO를 display_sequence 오름차순으로 정렬하여 반환하거나, 미발견시 빈 리스트를 반환한다.
     */
    @Query(value = """
        select new com.musinsa.muordi.platform.domain.category.CategoryDTO(category.id, category.name, category.display_sequence)
            from Category category 
            where category.name = :name
            order by category.display_sequence asc
    """)
    List<CategoryDTO> byName(@Param("name") String name);
}
