package com.musinsa.muordi.platform.domain;

import com.musinsa.muordi.platform.domain.brand.Brand;
import com.musinsa.muordi.platform.domain.brand.BrandRepository;
import com.musinsa.muordi.platform.domain.category.Category;
import com.musinsa.muordi.platform.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DomainService {
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    /**
     * 카테고리 목록을 조회한다. 조회결과는 항상 전시순서 오름차순으로 정렬되어있다.
     * @return 카테고리 리스트를 반환한다.
     */
    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    /**
     * 브랜드 목록을 조회한다.
     * @return 모든 브랜드 리스트 또는 빈 리스트를 반환한다.
     */
    public List<Brand> getBrands() {
        return this.brandRepository.findAll();
    }

    /**
     * 브랜드 이름을 조회한다. 동일한 이름을 가진 브랜드가 있는 경우 모두 반환한다.
     * @param name 브랜드 이름
     * @return 발견한 브랜드 리스트, 또는 빈 리스트를 반환한다.
     */
    public List<Brand> getBrands(String name) {
        return this.brandRepository.findByName(name);
    }

    /**
     * 브랜드 식별자를 조회한다.
     * @param id
     * @return 해당 식별자의 브랜드가 존재하는 경우 해당 브랜드를, 그 외에는 아무것도 담지 않은 Optional 을 반환한다.
     */
    public Optional<Brand> getBrand(int id) {
        return this.brandRepository.findById(id);
    }

}
