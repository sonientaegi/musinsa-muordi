package com.musinsa.muordi.contents.display.repository;

import com.musinsa.muordi.common.exception.RepositoryEntityIntegrityViolation;
import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import com.musinsa.muordi.common.jpa.AtomicUpdateById;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShowcaseRepositoryJpaWrapper implements ShowcaseRepository {
    private final ShowcaseRepositoryJpa repository;

    @Override
    public List<Showcase> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Showcase> findByProductId(long productId) {
        return this.repository.findByProduct_Id(productId);
    }

    @Override
    public List<Showcase> findByBrandId(int brandId) {
        return this.repository.findByProduct_Brand_Id(brandId);
    }

    @Override
    public List<Showcase> findByBrandName(String brandName) {
        return this.repository.findByProduct_Brand_Name(brandName);
    }

    @Override
    public List<Showcase> findByCategoryId(int categoryId) {
        return this.repository.findByCategory_Id(categoryId);
    }

    @Override
    public List<Showcase> findByCategoryName(String categoryName) {
        return this.repository.findByCategory_Name(categoryName);
    }

    @Override
    public Showcase create(Showcase showcase) {
        return this.repository.save(showcase);
    }

    /**
     * 상품 ID로 쇼케이스를 찾아 수정한다. 오류시 예외르 반환한다.
     * @param showcase 수정할 내용. 수정할 상품과 변경할 카테고리 정보를 포함한다.
     * @return 수정한 쇼케이스 entity. null 일 수 없다.
     * @see AtomicUpdateById#updateById
     */
    public Showcase update(Showcase showcase) {
        // 상품 ID로 쇼케이스 레코드 PK를 찾는다.
        long productId = showcase.getProduct().getId();
        int targetId = this.findByProductId(productId).orElseThrow(()->new RepositoryEntityNotExistException("SHOWCASE", productId)).getId();
        return this.repository.updateById(targetId, showcase);
    }

    @Override
    public Showcase delete(long productId) {
        try {
            Showcase showcase = this.findByProductId(productId).orElseThrow(() -> new RepositoryEntityNotExistException("SHOWCASE", productId));
            this.repository.deleteById(showcase.getId());
            return showcase;
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryEntityIntegrityViolation("PRODUCT", "DELETE", productId);
        }
    }
}