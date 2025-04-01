package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.jpa.AtomicUpdateById;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 브랜드 JpaRepository 확장 명세이다.
 */
@Repository
public interface BrandRepositoryJpa extends JpaRepository<Brand, Integer>, AtomicUpdateById<Brand, Integer> {
    /**
     * SELECT * FROM BRAND
     * WHERE name = :name
     */
    List<Brand> findByName(String name);
}
