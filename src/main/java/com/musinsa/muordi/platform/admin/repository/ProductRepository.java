package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.jpa.AtomicUpdateById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductRepository {
    /*
    주의.
    interface Repository, class RepositoryImpl 이런식으로 선언하면 bean 이름 규칙 때문에 JPA repository 구현체와 이름 충돌을 일으켜 순환 참조 오류를 표시함.
     */

    private final ProductRepositoryJpa repository;

    /**
     * 전체 상품을 조회한다.
     * @return 모든 상품 리스트.
     */
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    /**
     * 상품 식별자로 상품을 조회한다.
     * @param id 상품 식별자.
     * @return 발견 시 상품을 감싼, 그 외에는 빈 Optional을 반환한다.
     */
    public Optional<Product> findById(Long id) {
        return this.repository.findById(id);
    }

    /**
     * 브랜드 이름으로 상품을 조회한다.
     * @param name 브랜드 이름.
     * @return 발견 시 상품 리스트를, 그 외에는 빈 리스트를 반환한다.
     */
    public List<Product> findByBrandName(String name) {
        return this.repository.findProductByBrand_Name(name);
    }

    /**
     * 브랜드 식별자로 상품을 조회한다.
     * @param id 브랜드 식별자.
     * @return 발견 시 상품 리스트를, 그 외에는 빈 리스트를 반환한다.
     */
    public List<Product> findByBrandId(int id) {
        return this.repository.findProductByBrand_Id(id);
    }

    // TODO 브랜드가 존재하지 않는 경우 어케할것인가.
    /**
     * 상품을 생성한다. 상품생성시 브랜드 식별자는 필수이며, 브랜드가 존재하지 않는 경우
     * 어떻게 오류 처리를 할지 조금 있다 고민해보자구
     * @param product 생성하려고 하는 상품의 정보.
     * @return 신규로 생성한 상품.
     */
    public Product save(Product product) {
        Product actual = this.repository.save(product);
        return actual;
    }

    /**
     * 상품을 수정한다.
     * @param id 상품 식별자.
     * @param product 수정할 상품 내용.
     * @return 수정 성공시 수정한 상품을 감싼, 그 외에는 빈 Optional을 반환한다.
     * @see AtomicUpdateById#updateById
     */
    public Optional<Product> updateById(long id, Product product) {
        return this.repository.updateById(id, product);
    }

    /**
     * 상품을 삭제한다.
     * @param id 삭제할 상품의 식별자.
     */
    public void deleteById(long id) {
        this.repository.deleteById(id);
    }
}

