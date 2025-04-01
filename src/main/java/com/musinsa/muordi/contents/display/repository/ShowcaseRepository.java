package com.musinsa.muordi.contents.display.repository;

import com.musinsa.muordi.common.jpa.AtomicUpdateById;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShowcaseRepository {
    private final ShowcaseRepositoryJpa repository;

    /**
     * 전체 쇼케이스를 조회한다.
     * @return 모든 쇼케이스 리스트.
     */
    public List<Showcase> findAll() {
        return this.repository.findAll();
    }

    /**
     * 상품의 식별자로 쇼케이스를 조회한다.
     * @param productId 조회할 상품 식별자.
     * @return 쇼케이스 리스트.
     */
    public Optional<Showcase> findByProductId(long productId) {
        return this.repository.findByProduct_Id(productId);
    }

    /**
     * 상품의 브랜드 식별자로 쇼케이스를 조회한다.
     * @param brandId 조회할 브랜드 식별자.
     * @return 쇼케이스 리스트.
     */
    public List<Showcase> findByBrandId(int brandId) {
        return this.repository.findByProduct_Brand_Id(brandId);
    }

    /**
     * 상품의 브랜드 명칭으로 쇼케이스를 조회한다.
     * @param brandName 조회할 브랜드 명칭.
     * @return 쇼케이스 리스트.
     */
    public List<Showcase> findByBrandName(String brandName) {
        return this.repository.findByProduct_Brand_Name(brandName);
    }

    /**
     * 상품의 카테고리 식별자로 쇼케이스를 조회한다.
     * @param categoryId 조회할 카테고리 식별자.
     * @return 쇼케이스 리스트.
     */
    public List<Showcase> findByCategoryId(int categoryId) {
        return this.repository.findByCategory_Id(categoryId);
    }

    /**
     * 상품의 카테고리 명칭으로 쇼케이스를 조회한다.
     * @param categoryName 조회할 카테고리 명칭.
     * @return 쇼케이스 리스트.
     */
    public List<Showcase> findByCategoryName(String categoryName) {
        return this.repository.findByCategory_Name(categoryName);
    }

    /**
     * 상품의 쇼케이스를 삭제한다.
     * @param productId 상품 식별자.
     */
    public void deleteByProductId(long productId) {
        this.repository.deleteShowcaseByProduct_Id(productId);
    }

    /**
     * 쇼케이스를 저장한다.
     * @param showcase
     */
    public Showcase save(Showcase showcase) {
        return this.repository.save(showcase);
    }

    /**
     * 상품 식별자로 쇼케이스를 찾아 수정한다.
     * @param productId 수정할 쇼케이스의 상품식별자.
     * @param showcase 수정할 내용.
     * @return 수정 성공시 수정한 상품을 감싼, 그 외에는 빈 Optional을 반환한다.
     * @see AtomicUpdateById#updateById
     */
    public Optional<Showcase> updateByProductId(long productId, Showcase showcase) {
        Showcase target = this.findByProductId(productId).orElseThrow(()->new RuntimeException("Product with id " + productId + " not found"));
        target.setCategory(showcase.getCategory());
        return Optional.ofNullable(this.repository.updateById(target.getId(), target));
    }
}