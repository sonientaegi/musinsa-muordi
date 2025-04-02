package com.musinsa.muordi.contents.explore.repository;

import com.musinsa.muordi.contents.display.repository.ShowcaseRepositoryJpaWrapper;
import com.musinsa.muordi.contents.explore.dto.ItemDto;
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
        List<ItemDto> actuals = this.repository.getCheapestProductOfCategoryBrand();
        assertEquals(actuals.size(), 8*9);
    }

    @Test
    @DisplayName("Q1")
    void getCheapestByCategory() {
        List<ItemDto> expecteds = List.of(
                ItemDto.builder().categoryId(1).brandId(3).price(10000).build(),
                ItemDto.builder().categoryId(2).brandId(5).price(5000).build(),
                ItemDto.builder().categoryId(3).brandId(4).price(3000).build(),
                ItemDto.builder().categoryId(4).brandId(7).price(9000).build(),
                ItemDto.builder().categoryId(5).brandId(1).price(2000).build(),
                ItemDto.builder().categoryId(6).brandId(4).price(1500).build(),
                ItemDto.builder().categoryId(7).brandId(9).price(1700).build(),
                ItemDto.builder().categoryId(8).brandId(6).price(1900).build()
        );
        List<ItemDto> actuals = this.repository.getCheapestByCategory();
        assertIterableEquals(expecteds, actuals);
    }

    @Test
    @DisplayName("Q2")
    void getCheapestBrand() {
        List<ItemDto> expecteds = List.of(
                new ItemDto(1,4,10100),
                new ItemDto(2,4,5100),
                new ItemDto(3,4,3000),
                new ItemDto(4,4,9500),
                new ItemDto(5,4,2500),
                new ItemDto(6,4,1500),
                new ItemDto(7,4,2400),
                new ItemDto(8,4,2000)
        );
        List<ItemDto> actuals = this.repository.getCheapestBrand();
        assertIterableEquals(expecteds, actuals);
    }


    @Test
    @DisplayName("Q3.1")
    void getMaxPriceOfCategory() {
        ItemDto expected = new ItemDto(1,9,11400);
        Optional<ItemDto> optActual = this.repository.getMaxPriceOfCategory(1);
        assertTrue(optActual.isPresent());
        ItemDto actual = optActual.get();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Q3.2")
    void getMinPriceOfCategory() {
        ItemDto expected = new ItemDto(1,3,10000);
        Optional<ItemDto> optActual = this.repository.getMinPriceOfCategory(1);
        assertTrue(optActual.isPresent());
        ItemDto actual = optActual.get();
        assertEquals(expected, actual);
    }
}