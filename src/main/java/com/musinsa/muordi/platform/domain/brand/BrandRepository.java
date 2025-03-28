package com.musinsa.muordi.platform.domain.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 브랜드 DML을 정의한다.
 */
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    /**
     * 모든 결과를 반환한다.
     * @return 모든 브랜드 레코드의 DTO를 반환하거나, 미발견시 빈 배열을 반환한다.
     */
    @Query(value = """
        select new com.musinsa.muordi.platform.domain.brand.BrandDTO(brand.id, brand.name)
            from Brand brand
    """)
    List<BrandDTO> all();

    /**
     * 브랜드를 단건 조회한다.
     * @param id 조회할 브랜드의 식별자
     * @return 발견한 브랜드 레코드의 DTO를 반환하거나, null을 반환한다.
     */
    @Query(value = """
        select new com.musinsa.muordi.platform.domain.brand.BrandDTO(brand.id, brand.name)
            from Brand brand 
            where brand.id = :id
    """)
    BrandDTO byId(@Param("id") Integer id);

    /**
     * 브랜드 이름으로 브랜드를 조회한다.
     * @param name 브랜드 이름
     * @return 발견한 모든 브랜드 레코드의 DTO를 리스트로 반환하거나, 미발견시 빈 리스트를 반환한다.
     */
    @Query(value = """
        select new com.musinsa.muordi.platform.domain.brand.BrandDTO(brand.id, brand.name)
            from Brand brand 
            where brand.name = :name
    """)
    List<BrandDTO> byName(@Param("name") String name);
}
