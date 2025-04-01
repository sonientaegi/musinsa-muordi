package com.musinsa.muordi.contents.display.repository;

import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import lombok.NonNull;
import org.hibernate.PessimisticLockException;

import java.util.List;
import java.util.Optional;

/**
 * 쇼케이스 repository 의 DML을 명세한 인터페이스이다.
 */
public interface ShowcaseRepository {
    /**
     * 전체 쇼케이스를 조회한다.
     * @return 모든 쇼케이스 entity 리스트를 반환한다.
     */
    public List<Showcase> findAll();

    /**
     * 상품 ID로 쇼케이스를 조회한다.
     * @param productId 조회할 상품 ID.
     * @return Optional로 감싼 쇼케이스 entity, 미발견 시 빈 Optional을 반환한다. null 일 수 없다.
     */
    public Optional<Showcase> findByProductId(long productId);

    /**
     * 브랜드 ID로 쇼케이스를 조회한다. 해당 브랜드에 등록한 모든 상품 중 쇼케이스에 전시중인 상품 리스트를 반환한다.
     * @param brandId 브랜드 ID
     * @return 쇼케이스 entity 리스트를 반환한다.
     */
    public List<Showcase> findByBrandId(int brandId);

    /**
     * 브랜드 이름으로 쇼케이스를 조회한다. 해당 브랜드에 등록한 모든 상품 중 쇼케이스에 전시중인 상품 리스트를 반환한다.
     * 같은 이름의 다른 브랜드가 있다면 모두 반환한다.
     * @param brandName 브랜드 이름.
     * @return 쇼케이스 entity 리스트를 반환한다.
     */
    public List<Showcase> findByBrandName(String brandName);

    /**
     * 카테고리 ID로 쇼케이스를 조회한다. 해당 카테고리로 등록한 모든 전시중인 상품을 반환한다.
     * @param categoryId 카테고리 ID
     * @return 쇼케이스 entity 리스트를 반환한다.
     */
    public List<Showcase> findByCategoryId(int categoryId);

    /**
     * 카테고리 이름으로 쇼케이스를 조회한다. 해당 카테고리로 등록한 모든 전시중인 상품을 반환한다.
     * 같은 이름의 다른 카테고리가 있다면 모두 반환한다.
     * @param categoryName 카테고리 이름.
     * @return 쇼케이스 entity 리스트를 반환한다.
     */
    public List<Showcase> findByCategoryName(String categoryName);

    /**
     * 상품을 쇼케이스에 등록한다. 만약 상품이나 카테고리의 정보를 발견하지 못한다면 예외를 반환한다.
     * @param showcase 등록할 쇼케이스 정보. 상품과 카테고리 값은 필수다.
     * @return 등록한 쇼케이스 entity를 반환한다. null 일 수 없다.
     */
    public Showcase create(Showcase showcase);

    /**
     * 쇼케이스에 진열한 상품의 전시 정보를 수정한다. 만약 상품이나 카테고리의 정보를 발견하지 못한다면 예외를 반환한다.
     * @param showcase 수정할 전시정보.
     * @return 수정한 쇼케이스 entity를 반환한다. null 일 수 없다.
     * @throws RepositoryEntityNotExistException 수정하려는 상품이나 카테고리 정보가 존재하지 않는다.
     * @throws PessimisticLockException 락 획득에 실패했다.
     */
    public Showcase update(@NonNull Showcase showcase);

    /**
     * 상품의 쇼케이스 등록 정보를 삭제한다. 실패 시 예외를 반환한다.
     * @param productId 쇼케이스에서 제외 활 상품 ID.
     * @return 제외 한 상품 entity. null 일 수 없다.
     * @throws RepositoryEntityNotExistException 제외 하려는 상품이 존재하지 않는다.
     */
    public Showcase delete(long productId);
}
