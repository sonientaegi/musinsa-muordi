package com.musinsa.muordi.contents.explore.repository;

import com.musinsa.muordi.contents.explore.dto.ItemDto;

import java.util.List;
import java.util.Optional;

/**
 * 가격 탐색 API의 명세이다.
 */
public interface ExploreRepository {
    /**
     * 문제 1.<br>
     * 전시 상품중 [카테고리 별 최저가격과 브랜드]를 [전시 카테고리 오름차순, 브랜드 내림차순]으로 정렬하여 반환한다.
     * 전시 담당자가 하나의 카테고리에 동일한 브랜드의 상품을 2개 이상 전시하였더라도 최저 가격인 제품의 가격만 추출한다.
     * @return 카테고리 정렬 기준으로 중복 제거를 수행한, 카테고리 별 최저 가격 브랜드 리스트.
     */
    List<ItemDto> getCheapestByCategory();

    /**
     * 문제 2.<br>
     * 브랜드별로 모든 카테고리의 제품 구매시, 최저 가격인 브랜드의 금액 목록을 반환한다. 만약 합계 필수 조건인
     * [모든 카테고리에 하나 이상의 상품을 전시해야한다] 를 만족하지 못하는 브랜드는 탐색 대상에서 제외한다
     * @return 최저가 브랜드의 전시상품 가격 리스트
     */
    List<ItemDto> getCheapestBrand();

    /**
     * 문제 3-1.<br>
     * 지정한 카테고리의 최고 가격 상품의 정보를 반환한다. 카테고리에 동록한 상품이 하나도 없다면 빈 Optional을 반환한다.
     * @param categoryId 카테고리 식별자.
     * @return 조회 시 가격정보를 감싼, 그 외에는 빈 Optional
     */
    Optional<ItemDto> getMaxPriceOfCategory(int categoryId);

    /**
     * 문제 3-2.<br>
     * 지정한 카테고리의 최저 가격 상품의 정보를 반환한다. 카테고리에 동록한 상품이 하나도 없다면 빈 Optional을 반환한다.
     * @param categoryId 카테고리 식별자.
     * @return 조회 시 가격정보를 감싼, 그 외에는 빈 Optional
     */
    Optional<ItemDto> getMinPriceOfCategory(int categoryId);
}
