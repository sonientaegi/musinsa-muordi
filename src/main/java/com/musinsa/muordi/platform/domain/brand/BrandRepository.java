package com.musinsa.muordi.platform.domain.brand;

import com.musinsa.muordi.utils.jpa.AtomicUpdateById;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 브랜드 DML을 정의한다.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>, AtomicUpdateById<Brand, Integer> {
    /**
     * 명칭으로 브랜드를 조회한다.
     * @param name 조회할 브랜드 명칭
     * @return 발견한 브랜드 리스트, 또는 빈 리스트
     */
    List<Brand> findByName(String name);
}
