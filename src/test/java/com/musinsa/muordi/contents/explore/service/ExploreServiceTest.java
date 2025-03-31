package com.musinsa.muordi.contents.explore.service;

import com.musinsa.muordi.contents.explore.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExploreServiceTest {
    @Autowired
    private ExploreService exploreService;

    @Test
    @DisplayName("구현 1. 카테고리 별 최저가격 브랜드와 상품가격, 총액을 조회한다.")
    void getCheapestByCategory() {
        List<PriceRecordWithNameDto> expecteds = List.of(
                PriceRecordWithNameDto.builder().categoryName("상의").brandName("C").categoryId(1).brandId(3).price(10000).build(),
                PriceRecordWithNameDto.builder().categoryName("아우터").brandName("E").categoryId(2).brandId(5).price(5000).build(),
                PriceRecordWithNameDto.builder().categoryName("바지").brandName("D").categoryId(3).brandId(4).price(3000).build(),
                PriceRecordWithNameDto.builder().categoryName("스니커즈").brandName("G").categoryId(4).brandId(7).price(9000).build(),
                PriceRecordWithNameDto.builder().categoryName("가방").brandName("A").categoryId(5).brandId(1).price(2000).build(),
                PriceRecordWithNameDto.builder().categoryName("모자").brandName("D").categoryId(6).brandId(4).price(1500).build(),
                PriceRecordWithNameDto.builder().categoryName("양말").brandName("I").categoryId(7).brandId(9).price(1700).build(),
                PriceRecordWithNameDto.builder().categoryName("액세서리").brandName("F").categoryId(8).brandId(6).price(1900).build()
        );
        CheapestBrandOfCategoryDto actuals = this.exploreService.getCheapestByCategory();
        assertNotNull(actuals);
        assertEquals(34100, actuals.getTotalAmount());
        assertIterableEquals(expecteds, actuals.getPriceRecords());
    }

    @Test
    @DisplayName("구현 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저 가격 브랜드를 찾는다.")
    void getCheapestBrand() {
        List<PriceRecordWithNameDto> expecteds = List.of(
                PriceRecordWithNameDto.builder().categoryName("상의").brandName("D").categoryId(1).brandId(4).price(11000).build(),
                PriceRecordWithNameDto.builder().categoryName("아우터").brandName("D").categoryId(2).brandId(4).price(5100).build(),
                PriceRecordWithNameDto.builder().categoryName("바지").brandName("D").categoryId(3).brandId(4).price(3000).build(),
                PriceRecordWithNameDto.builder().categoryName("스니커즈").brandName("D").categoryId(4).brandId(4).price(9500).build(),
                PriceRecordWithNameDto.builder().categoryName("가방").brandName("D").categoryId(5).brandId(4).price(2500).build(),
                PriceRecordWithNameDto.builder().categoryName("모자").brandName("D").categoryId(6).brandId(4).price(1500).build(),
                PriceRecordWithNameDto.builder().categoryName("양말").brandName("D").categoryId(7).brandId(4).price(2400).build(),
                PriceRecordWithNameDto.builder().categoryName("액세서리").brandName("D").categoryId(8).brandId(4).price(2000).build()
        );
        CheapestBrandDto actuals = this.exploreService.getCheapestBrand();
        assertNotNull(actuals);
        assertEquals(36100, actuals.getTotalAmount());
        assertIterableEquals(expecteds, actuals.getPriceRecords());
    }

    @Test
    void priceRangeofCategoryByName() {
        PriceRangeOfCategoryDto expected = PriceRangeOfCategoryDto.builder()
                .categoryName("상의")
                .categoryId(1)
                .minPriceRecord(PriceRecordWithNameDto.builder().categoryName("상의").categoryId(1).brandName("C").brandId(3).price(10000).build())
                .maxPriceRecord(PriceRecordWithNameDto.builder().categoryName("상의").categoryId(1).brandName("I").brandId(9).price(11400).build())
                .build();
        PriceRangeOfCategoryDto actual = this.exploreService.PriceRangeofCategoryByName("상의");
        assertNotNull(actual);
        assertEquals(expected, actual);

    }

}