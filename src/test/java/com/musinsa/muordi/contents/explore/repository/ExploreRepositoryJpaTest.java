package com.musinsa.muordi.contents.explore.repository;

import com.musinsa.muordi.contents.display.repository.Showcase;
import com.musinsa.muordi.contents.display.repository.ShowcaseRepository;
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
    private ShowcaseRepository showcaseRepository;

    @Test
    void getCheapestProductOfCategoryBrand() {
        List<PriceRecordDto> actuals = this.repository.getCheapestProductOfCategoryBrand();
        assertEquals(actuals.size(), 8*9);
    }

    @Test
    @DisplayName("Q1")
    void getCheapestOfAll() {
        List<PriceRecordDto> expects = List.of(
                new PriceRecordDto(1, 3, 10000),
                new PriceRecordDto(2, 5, 5000),
                new PriceRecordDto(3, 4, 3000),
                new PriceRecordDto(4, 7, 9000),
                new PriceRecordDto(5, 1, 2000),
                new PriceRecordDto(6, 4, 1500),
                new PriceRecordDto(7, 9, 1700),
                new PriceRecordDto(8, 6, 1900)
        );
        List<PriceRecordDto> actuals = this.repository.getCheapestOfAll();
        assertIterableEquals(expects, actuals);
    }

    @Test
    @DisplayName("Q2")
    void getCheapestBrand() {
        List<PriceRecordDto> expects = List.of(
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
        assertIterableEquals(expects, actuals);
    }


    @Test
    @DisplayName("Q3.1")
    void getMaxPriceOfCategory() {
        PriceRecordDto expect = new PriceRecordDto(1,9,11400);
        Optional<PriceRecordDto> optActual = this.repository.getMaxPriceOfCategory(1);
        assertTrue(optActual.isPresent());
        PriceRecordDto actual = optActual.get();
        assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Q3.2")
    void getMinPriceOfCategory() {
        PriceRecordDto expect = new PriceRecordDto(1,3,10000);
        Optional<PriceRecordDto> optActual = this.repository.getMinPriceOfCategory(1);
        assertTrue(optActual.isPresent());
        PriceRecordDto actual = optActual.get();
        assertEquals(expect, actual);
    }
}