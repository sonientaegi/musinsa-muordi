package com.musinsa.muordi.platform.domain.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 브랜드 DML을 정의한다.
 */
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    /**
     * BrandDTO를 전달받아 DB에 저장한다. id 필드에 값이 있다면 기존 브랜드를 수정하며 null이면 신규레코드를 생성한다.
     * @param brandDTO 저장 대상 브랜드 DTO
     * @return 생성 또는 수정한 결과의 DTO
     */
    default BrandDTO save(BrandDTO brandDTO) {
        Brand entity = this.save(new Brand(brandDTO.getId(), brandDTO.getName()));
        return new BrandDTO(entity.getId(), entity.getName());
    }

    /**
     * BrandDTO 리스트를 전달받아 DB에 저장한다. id 필드에 값이 있다면 기존 브랜드를 수정하며 null이면 신규레코드를 생성한다.
     * @param brandDTOList 저장 대상 브랜드 DTO 리스트
     * @return 생성 또는 수정한 결과의 DTO 리스트
     */
    default List<BrandDTO> saveAll(List<BrandDTO> brandDTOList) {
        return this.saveAll(brandDTOList.stream().map(dto -> new Brand(dto.getId(), dto.getName())).toList())
                .stream().map(entity -> new BrandDTO(entity.getId(), entity.getName())).toList();
    }

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
