package com.musinsa.muordi.contents.display.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShowcaseRepository extends JpaRepository<Showcase, Integer> {
    /**
     * 상품 식별자로 쇼케이스를 조회한다. 상품 식별자는 유일키이므로 이 동작은 항상 단건 조회를 보장한다.
     * @param productId 조회할 상품 식별자.
     * @return 존재시 해당 entity를 감싸는, 그 외에는 비어있는 Optional을 반환한다.
     */
    Optional<Showcase> findByProduct_Id(long productId);

    /**
     * 브랜드 식별자로 쇼케이스를 조회한다.
     * @param brandId 브랜드 식별자.
     * @return 해당 브랜드 식별자를 가지는 쇼케이스 entity 리스트.
     */
//    @Query(value =
//            """
//            select showcase from Showcase showcase inner join Product product on showcase.product.id = product.id where product.brand.id = :brandId
//            """)
//    List<Showcase> findAllByBrand_Id(int brandId);
    List<Showcase> findByProduct_Brand_Id(int brandId);

    /**
     * 브랜드 이름으로 쇼케이스를 조회한다.
     * @param brandName 브랜드 이름.
     * @return 해당 브랜드 이름을 가지는 쇼케이스 entity 리스트.
     */
//    @Query(value =
//            """
//            select showcase
//            from Showcase showcase
//            inner join Product product on showcase.product.id = product.id
//            inner join Brand brand on product.brand.id = brand.id
//            where brand.name = :brandName
//            """)
//    List<Showcase> findAllByBrand_Name(String brandName);
    List<Showcase> findByProduct_Brand_Name(String brandName);

    /**
     * 카테고리 식별자로 쇼케이스를 조회한다.
     * @param categoryId 조회할 카테고리 식별자.
     * @return 해당 카테고리에 속하는 쇼케이스 entity 리스트.
     */
    List<Showcase> findByCategory_Id(int categoryId);

    /**
     * 카테고리 이름으로 쇼케이스를 조회한다.
     * @param categoryName 조회할 카테고리 이름.
     * @return 해당 카테고리에 속하는 쇼케이스 entity 리스트.
     */
    List<Showcase> findByCategory_Name(String categoryName);
}
