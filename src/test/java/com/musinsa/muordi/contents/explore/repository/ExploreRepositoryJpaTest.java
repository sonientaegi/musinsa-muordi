package com.musinsa.muordi.contents.explore.repository;

import com.musinsa.muordi.contents.display.repository.ShowcaseRepositoryJpaWrapper;
import com.musinsa.muordi.contents.explore.dto.PriceRecordDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExploreRepositoryJpaTest {
    @Autowired
    private ExploreRepositoryJpa repository;
    private ShowcaseRepositoryJpaWrapper showcaseRepositoryJpaWrapper;

    @Test
    void getCheapestProductOfCategoryBrand() {
        List<PriceRecordDto> actuals = this.repository.getCheapestProductOfCategoryBrand();
        assertEquals(actuals.size(), 8*9);
    }

    @Test
    @DisplayName("Q1")
    void getCheapestByCategory() {
        List<PriceRecordDto> expecteds = List.of(
                PriceRecordDto.builder().categoryId(1).brandId(3).price(10000).build(),
                PriceRecordDto.builder().categoryId(2).brandId(5).price(5000).build(),
                PriceRecordDto.builder().categoryId(3).brandId(4).price(3000).build(),
                PriceRecordDto.builder().categoryId(4).brandId(7).price(9000).build(),
                PriceRecordDto.builder().categoryId(5).brandId(1).price(2000).build(),
                PriceRecordDto.builder().categoryId(6).brandId(4).price(1500).build(),
                PriceRecordDto.builder().categoryId(7).brandId(9).price(1700).build(),
                PriceRecordDto.builder().categoryId(8).brandId(6).price(1900).build()
        );
        List<PriceRecordDto> actuals = this.repository.getCheapestByCategory();
        assertIterableEquals(expecteds, actuals);
    }

    @Test
    @DisplayName("Q2")
    void getCheapestBrand() {
        List<PriceRecordDto> expecteds = List.of(
                new PriceRecordDto(1,4,10100),
                new PriceRecordDto(2,4,5100),
                new PriceRecordDto(3,4,3000),
                new PriceRecordDto(4,4,9500),
                new PriceRecordDto(5,4,2500),
                new PriceRecordDto(6,4,1500),
                new PriceRecordDto(7,4,2400),
                new PriceRecordDto(8,4,2000)
        );
        List<PriceRecordDto> actuals = this.repository.getCheapestBrand();
        assertIterableEquals(expecteds, actuals);
    }


    @Test
    @DisplayName("Q3.1")
    void getMaxPriceOfCategory() {
        PriceRecordDto expected = new PriceRecordDto(1,9,11400);
        Optional<PriceRecordDto> optActual = this.repository.getMaxPriceOfCategory(1);
        assertTrue(optActual.isPresent());
        PriceRecordDto actual = optActual.get();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Q3.2")
    void getMinPriceOfCategory() {
        PriceRecordDto expected = new PriceRecordDto(1,3,10000);
        Optional<PriceRecordDto> optActual = this.repository.getMinPriceOfCategory(1);
        assertTrue(optActual.isPresent());
        PriceRecordDto actual = optActual.get();
        assertEquals(expected, actual);
    }
}