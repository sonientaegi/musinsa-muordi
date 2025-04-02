package com.musinsa.muordi.contents.explore.repository;

import com.musinsa.muordi.contents.display.repository.CategoryRepository;
import com.musinsa.muordi.contents.display.repository.CategoryRepositoryJpa;
import com.musinsa.muordi.contents.explore.dto.ItemDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * JPA 기반의 가격 탐색 구현체 이다.
 */
@Component
@RequiredArgsConstructor
public class ExploreRepositoryJpa implements ExploreRepository {
    private final EntityManager entityManager;
    private final CategoryRepository categoryRepository;

    /**
     * 전시중인 상품을 카테고리별 오름차순, 가격 오름차순, 브랜드 내림차순으로 정렬하여 반환한다.
     * @return
     */
    public List<ItemDto> getCheapestProductOfCategoryBrand() {
        /* H2 Native SQL
select category_id as CATEGORY_ID, PRODUCT.brand_id as BRAND_ID, PRODUCT.price as PRICE
from SHOWCASE
inner join PRODUCT on SHOWCASE.product_id = PRODUCT.id
inner join CATEGORY on SHOWCASE.category_id = CATEGORY.id
order by CATEGORY.display_sequence asc, price asc, brand_id desc;
         */

        // BRAND.name 과 CATEGORY.name 을 INNER JOIN해서 가져올 수도 있다.
        // 이 부분은 데이터베이스와 어플리케이션의 부하 트레이드 오프 관계라 실제 환경에 따라 구현을 결정한다.
        return this.entityManager.createQuery("""
SELECT new com.musinsa.muordi.contents.explore.dto.ItemDto(showcase.category.id, product.brand.id, product.price)
FROM Showcase showcase
INNER JOIN showcase.product product
ORDER BY showcase.category.displaySequence asc, product.price asc, product.brand.id desc
""").getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public List<ItemDto> getCheapestByCategory() {
        /* H2 Native SQL
select distinct on (CATEGORY_ID) CATEGORY_ID, BRAND_ID, PRICE from (
    select category_id as CATEGORY_ID, PRODUCT.brand_id as BRAND_ID, PRODUCT.price as PRICE
    from SHOWCASE
    inner join PRODUCT on SHOWCASE.product_id = PRODUCT.id
    inner join CATEGORY on SHOWCASE.category_id = CATEGORY.id
    order by CATEGORY.display_sequence asc, price asc, brand_id desc
);
         */
        // JPQL이 distinct on 을 지원하지않아 distinct on ( SHOWCASE.category_id ) 는 로직으로 수행 함.
        // 정렬 순서를 유지해야 하므로 iteration 으로 처리함.
        Set<Integer> distinct = new HashSet<>();
        List<ItemDto> results = new ArrayList<>();
        this.getCheapestProductOfCategoryBrand().forEach(result -> {
            ItemDto record = result;
            if (!distinct.contains(record.getCategoryId())) {
                distinct.add(record.getCategoryId());
                results.add(record);
            }
        });
        return results;
    }

    /**
     * {@inheritDoc}
     */
    public List<ItemDto> getCheapestBrand() {
        /* H2 Native SQL
select BRAND_ID, sum(PRICE), count(*) from (
    select category_id as CATEGORY_ID, PRODUCT.brand_id as BRAND_ID, PRODUCT.price as PRICE
    from SHOWCASE
    inner join PRODUCT on SHOWCASE.product_id = PRODUCT.id
    inner join CATEGORY on SHOWCASE.category_id = CATEGORY.id
    order by CATEGORY.display_sequence asc, price asc, brand_id desc
)
group by BRAND_ID
having count(*)=8
order by sum(PRICE)
limit 1;
         */

        // 브랜드 별로 상품 가격을 분류한다. 원본 데이터가 카테고리, 가격 오름차순 정렬이므로 브랜드별로 분류한 상품결과도 카테고리 오름차순 정렬을
        // 유지한다. 브랜드 정렬 결과를 유지하기 위해 순서가 있는 맵을 사용한다.
        Map<Integer, List<ItemDto>> productsByBrand = new LinkedHashMap<>();
        this.getCheapestProductOfCategoryBrand().forEach(result -> {
            productsByBrand.computeIfAbsent(result.getBrandId(), k -> new ArrayList<>()).add(result);
        });


        // 브랜드별로 레코드 개수와 금액의 합계를 확인한다. 전시 필수조건을 만족하고, 합계금액이 최저인, 가장 먼저 발견한 브랜드를 결과로 선택한다.
        int numOfCategories = this.categoryRepository.findAll().size();
        List<int[]> sumOfPrice = new ArrayList<>();
        productsByBrand.forEach((brandId, priceRecord) -> {
            if (priceRecord.size() == numOfCategories) {
                sumOfPrice.add(new int[] {brandId, priceRecord.stream().mapToInt(ItemDto::getPrice).sum()});
            }
        });

        if (sumOfPrice.isEmpty()) {
            return new ArrayList<>();
        } else {
            // 이 정렬은 stable 하다.
            sumOfPrice.sort((o1, o2) -> o1[1] - o2[1]);
            return productsByBrand.get(sumOfPrice.get(0)[0]);
        }
    }

    // 특정 카테고리의 상품가격 최대, 최솟값 구하는 쿼리의 공통 부분.
    private static final String QueryPriceOfCategory = """
SELECT new com.musinsa.muordi.contents.explore.dto.ItemDto(showcase.category.id, product.brand.id, product.price)
FROM Showcase showcase
INNER JOIN showcase.product product
WHERE showcase.category.id = :categoryId 
ORDER BY product.price %s
limit 1
""";

    /**
     * {@inheritDoc}
     */
    public Optional<ItemDto> getMaxPriceOfCategory(int categoryId) {
        /* H2 Native SQL
select CATEGORY.NAME, BRAND.name as BRAND_NAME, product.price as PRICE
from SHOWCASE
inner join PRODUCT on SHOWCASE.product_id = PRODUCT.id
inner join CATEGORY on SHOWCASE.category_id = CATEGORY.id
inner join BRAND on PRODUCT.brand_id = BRAND.id
where CATEGORY.name='상의'
order by PRICE desc
limit 1;
         */

        // 최댓값 정렬
        ItemDto result = (ItemDto) this.entityManager.createQuery(QueryPriceOfCategory.formatted("desc"))
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        return Optional.ofNullable(result);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<ItemDto> getMinPriceOfCategory(int categoryId) {
        // 최솟값 정렬
        ItemDto result = (ItemDto) this.entityManager.createQuery(QueryPriceOfCategory.formatted("asc"))
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        return Optional.ofNullable(result);
    }
}
