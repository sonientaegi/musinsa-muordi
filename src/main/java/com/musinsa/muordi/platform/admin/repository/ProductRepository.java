package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.exception.RepositoryEntityIntegrityViolation;
import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import lombok.NonNull;
import org.hibernate.PessimisticLockException;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    /**
     * 모든 상품을 반환한다.
     * @return 상품 entity 리스트를 반환한다.
     */
    List<Product> findAll();

    /**
     * ID로 상품을 조회한다. ID는 상품의 유일한 식별자이며 두개이상의 같은 값은 존재하지 않는다.
     * @param id 상품 ID
     * @return Optional로 감싼 상품 entity, 미발견 시 빈 Optional을 반환한다. null 일 수 없다.
     */
    Optional<Product> findById(long id);

    /**
     * 브랜드 ID로 상품을 조회한다.
     * @param brandId 브랜드 ID.
     * @return 발견한 상품 entity 리스트를 반환한다. 미발견 시 빈 리스트를 반환한다.
     */
    List<Product> findByBrandId(int brandId);

    /**
     * 브랜드 이름으로 상품을 조회한다.
     * @param brandName 브랜드 이름.
     * @return 발견한 상품 entity 리스트를 반환한다. 미발견 시 빈 리스트를 반환한다.
     */
    List<Product> findByBrandName(String brandName);

    /**
     * 새로운 상품을 생성한다. 실패시 Repository 구현체에서 예외를 반환한다.
     * @param product 생성할 상품.
     * @return 생성한 상품 entity. 상품 ID를 포함한다. null 일 수 없다.
     */
    Product create(@NonNull Product product);

    /**
     * 기존의 상품을 수정한다. 이 동작은 원자적 실행을 지원한다. 만약 ID로 상품을 발견하지 못한다면 예외를 반환한다.
     * @param id 수정할 상품 ID.
     * @param product 수정할 상품 내용.
     * @return 수정한 상품 entity. null 일 수 없다.
     * @throws RepositoryEntityNotExistException 수정하려는 상품이 존재하지 않는다.
      * @throws PessimisticLockException 락 획득에 실패했다.
     */
    Product update(long id, @NonNull Product product);

    /**
     * 기존의 상품을 삭제하고, 기존 상품의 entity를 반환한다. 실패시 예외를 반환한다.
     * @param id 삭제할 상품 ID.
     * @return 삭제 한 상품 entity. null 일 수 없다.
     * @throws RepositoryEntityNotExistException 삭제하려는 상품이 존재하지 않는다.
     * @throws RepositoryEntityIntegrityViolation 정합성 오류로 인해 상품을 삭제할 수 없다.
     */
    Product delete(long id);
}
