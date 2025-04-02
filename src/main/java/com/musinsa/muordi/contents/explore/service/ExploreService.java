package com.musinsa.muordi.contents.explore.service;

import com.musinsa.muordi.common.exception.NotFoundException;
import com.musinsa.muordi.common.exception.ShowcaseEmptyException;
import com.musinsa.muordi.contents.display.dto.CategoryDto;
import com.musinsa.muordi.contents.display.service.DisplayService;
import com.musinsa.muordi.contents.explore.dto.*;
import com.musinsa.muordi.contents.explore.repository.ExploreRepository;
import com.musinsa.muordi.platform.admin.dto.BrandDto;
import com.musinsa.muordi.platform.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 탐색 도메인 서비스.
 */
@RequiredArgsConstructor
@Service
public class ExploreService {
    private final ExploreRepository exploreRepository;
    private final DisplayService displayService;
    private final AdminService adminService;

    /**
     * 금액조회 결과를 카테고리 이름과 브랜드 이름을 추가한 DTO로 변환한다.
     * @param results
     * @return
     */
    protected List<ItemNamedDto> populateNameFieldAll(List<ItemDto> results) {
        List<ItemNamedDto> populatedResults = new ArrayList<>();
        results.forEach(record -> {
            populatedResults.add(this.populateNameField(record));
        });
        return populatedResults;
    }

    /**
     * 금액조회 결과를 카테고리 이름과 브랜드 이름을 추가한 DTO로 변환한다.
     * @param result
     * @return
     */
    protected ItemNamedDto populateNameField(ItemDto result) {
        CategoryDto category = this.displayService.getCategory(result.getCategoryId());
        BrandDto brand = this.adminService.getBrand(result.getBrandId());
        return ItemNamedDto.builder()
                .categoryName(category.getName())
                .brandName(brand.getName())
                .categoryId(result.getCategoryId())
                .brandId(result.getBrandId())
                .price(result.getPrice())
                .build();
    }

    /**
     * 카테고리별로 가장 저렴한 상품의 가격 정보와 가격의 합을 반환한다.
     * @return 카테고리 명칭, 브랜드 명칭을 포함하는 가격정보.
     * @throws ShowcaseEmptyException 전시중이거나, 전시필수조건을 만족하는 상품이 하나도 없다.
     */
    public CategoryBrandCheapestDto getCheapestByCategory() {
        List<ItemDto> results = this.exploreRepository.getCheapestByCategory();
        if (results.isEmpty()) {
            throw new ShowcaseEmptyException();
        }

        // 결과에 이름을 넣는다.
        List<ItemNamedDto> populatedResults = populateNameFieldAll(results);

        // 금액 합계를 낸다.
        int totalAmount = populatedResults.stream().mapToInt(ItemNamedDto::getPrice).sum();

        // 결과 반환.
        return CategoryBrandCheapestDto.builder()
                .priceRecords(populatedResults)
                .totalAmount(totalAmount)
                .build();
    }

    /**
     * 단일 브랜드로 모든 카테고리의 제품을 구매할때 최저가인 브랜드와 상품의 금액, 그리고 합계를 반환한다. 단 전시 필수조건인 "모든 카테고리에 하나 이상의 제품을 전시"를
     * 만족하는 브랜드에 한하여 결과를 조회한다.
     * @return 카테고리 명칭, 브랜드 명칭을 포함하는 가격정보.
     * @throws ShowcaseEmptyException 전시중이거나, 전시필수조건을 만족하는 상품이 하나도 없다.
     */
    public BrandCheapestDto getCheapestBrand() {
        List<ItemDto> results = this.exploreRepository.getCheapestBrand();
        if (results.isEmpty()) {
            throw new ShowcaseEmptyException();
        }

        // 결과에 이름을 넣는다.
        List<ItemNamedDto> populatedResults = populateNameFieldAll(results);

        // 금액 합계를 낸다.
        int totalAmount = populatedResults.stream().mapToInt(ItemNamedDto::getPrice).sum();

        // 결과 반환.
        return BrandCheapestDto.builder()
                .priceRecords(populatedResults)
                .brandId(populatedResults.getFirst().getBrandId())
                .brandName(populatedResults.getFirst().getBrandName())
                .totalAmount(totalAmount)
                .build();
    }

    /**
     * 지정한 카테고리의 상품 중 상품 최댓값과 최솟값을 조회한다.
     * @param categoryName 대상 카테고리 이름..
     * @return 최댓값, 최솟값 가격정보.
     * @throws NotFoundException 요청한 카테고리를 발견하지 못하였다.
     * @throws ShowcaseEmptyException 전시중이거나, 전시필수조건을 만족하는 상품이 하나도 없다.
     */
    public CategoryPriceRangeDto PriceRangeofCategoryByName(String categoryName) {
        // 카테고리 식별자를 구한다.
        CategoryDto category = this.displayService.getCategoryByName(categoryName);
        int categoryId = category.getId();

        // 최대, 최소금액 구하는 작업은 동일한 API이므로 병렬로 동시 수행할 수 있다.
        final ItemNamedDto[] results = {null, null};
        List<Thread> threads = List.of(
                // 최댓값 구하기
                new Thread(() -> {
                    results[0] = this.populateNameField(this.exploreRepository.getMinPriceOfCategory(categoryId).orElse(null));
                }),
                // 최솟값 구하기
                new Thread(() -> {
                    results[1] = this.populateNameField(this.exploreRepository.getMaxPriceOfCategory(categoryId).orElse(null));
                })
        );

        // 동시 수행
        for (Thread thread : threads) {
            thread.start();
        }

        // 두 작업이 모두 종료할 때 까지 대기.
        try {
            for (Thread thread : threads) {
                thread.join(Duration.ofSeconds(10));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (results[0] == null || results[1] == null) {
            throw new ShowcaseEmptyException();
        }

        return CategoryPriceRangeDto.builder()
                .minPriceRecord(results[0])
                .maxPriceRecord(results[1])
                .categoryId(results[0].getCategoryId())
                .categoryName(results[0].getCategoryName())
                .build();
    }
}
