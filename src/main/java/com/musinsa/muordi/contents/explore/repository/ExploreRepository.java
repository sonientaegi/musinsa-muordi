package com.musinsa.muordi.contents.explore.repository;

import com.musinsa.muordi.contents.explore.dto.PriceRecordDto;

import java.util.List;
import java.util.Optional;

public interface ExploreRepository {
    /**
     * 카테고리 별 최저 가격 상품의 정보를 반환한다. 결과는 항상 카테고리 전시순서 오름차순 정렬을 보장한다.
     * @return 가격정보 리스트.
     */
    public List<PriceRecordDto> getCheapestByCategory();

    /**
     * 브랜드 별로 모든 카테고리의 상품을 구매할때 최저가 브랜드를 반환한다. 단 전시 필수조건인 "모든 카테고리에 하나 이상의 제품을 전시"를
     * 만족하는 브랜드에 한하여 결과를 조회한다.
     * @return 최저가 브랜드의 가격정보 리스트
     */
    public List<PriceRecordDto> getCheapestBrand();

    /**
     * 지정한 카테고리의 최고 가격 상품의 정보를 반환한다. 카테고리에 동록한 상품이 하나도 없다면 빈 Optional을 반환한다.
     * @param categoryId 카테고리 식별자.
     * @return 조회 시 가격정보를 감싼, 그 외에는 빈 Optional
     */
    public Optional<PriceRecordDto> getMaxPriceOfCategory(int categoryId);

    /**
     * 지정한 카테고리의 최저 가격 상품의 정보를 반환한다. 카테고리에 동록한 상품이 하나도 없다면 빈 Optional을 반환한다.
     * @param categoryId 카테고리 식별자.
     * @return 조회 시 가격정보를 감싼, 그 외에는 빈 Optional
     */
    public Optional<PriceRecordDto> getMinPriceOfCategory(int categoryId);
}
