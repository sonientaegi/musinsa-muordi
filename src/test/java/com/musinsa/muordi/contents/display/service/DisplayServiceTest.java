package com.musinsa.muordi.contents.display.service;

import com.musinsa.muordi.contents.display.dto.CategoryDto;
import com.musinsa.muordi.contents.display.dto.ShowcaseDto;
import com.musinsa.muordi.contents.display.repository.Showcase;
import com.musinsa.muordi.contents.display.repository.ShowcaseRepositoryJpaWrapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test-display")
class DisplayServiceTest {
    @Autowired
    private DisplayService displayService;

    // 서비스 테스트 용도로만 사용.
    @Autowired
    private ShowcaseRepositoryJpaWrapper showcaseRepositoryJpaWrapper;

    /**
     * 무작위로 한개의 쇼케이스 레코드를 선택한다.
     *
     */
    private ShowcaseDto randShowcase() {
        List<Showcase> showcases = this.showcaseRepositoryJpaWrapper.findAll();
        return ShowcaseDto.fromEntity(showcases.get(new Random().nextInt(showcases.size())));
    }

    @Test
    @DisplayName("모든 카테고리 조회")
    void getCategories() {
        List<CategoryDto> actuals = this.displayService.getCategories();
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
    }

    @Test
    @DisplayName("모든 쇼케이스 조회")
    void getShowcases() {
        List<ShowcaseDto> actuals = this.displayService.getShowcases();
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
    }

    @Test
    @DisplayName("상품 식별자로 쇼케이스 조회")
    void getShowcase() {
        ShowcaseDto expected = this.randShowcase();
        Optional<ShowcaseDto> optActual = this.displayService.getShowcase(expected.getProductId());
        assertTrue(optActual.isPresent());
        ShowcaseDto actual = optActual.get();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("카테고리 식별자로 쇼케이스 조회")
    void getShowcasesByCategoryId() {
        ShowcaseDto expected = this.randShowcase();
        List<ShowcaseDto> actuals = this.displayService.getShowcasesByCategoryId(expected.getCategoryId());
        assertNotNull(actuals);
        actuals.forEach(actual -> assertEquals(expected.getCategoryId(), actual.getCategoryId()));
    }

    @Test
    @DisplayName("카테고리 명칭으로 쇼케이스 조회")
    void getShowcasesByCategoryName() {
        ShowcaseDto expected = this.randShowcase();
        List<ShowcaseDto> actuals = this.displayService.getShowcasesByCategoryName(expected.getCategoryName());
        assertNotNull(actuals);
        actuals.forEach(actual -> assertEquals(expected.getCategoryName(), actual.getCategoryName()));
    }

    @Test
    @DisplayName("브랜드 아이디로 쇼케이스 조회")
    void getShowcasesByBrandId() {
        ShowcaseDto expected = this.randShowcase();
        List<ShowcaseDto> actuals = this.displayService.getShowcasesByBrandId(expected.getBrandId());
        assertNotNull(actuals);
        actuals.forEach(actual -> assertEquals(expected.getBrandId(), actual.getBrandId()));
    }

    @Test
    @DisplayName("브랜드 명칭으로 쇼케이스 조회")
    void getShowcasesByBrandName() {
        ShowcaseDto expected = this.randShowcase();
        List<ShowcaseDto> actuals = this.displayService.getShowcasesByBrandName(expected.getBrandName());
        assertNotNull(actuals);
        actuals.forEach(actual -> assertEquals(expected.getBrandName(), actual.getBrandName()));
    }

    @Test
    @DisplayName("쇼케이스 생성")
    @Transactional
    void createShowcase() {
        ShowcaseDto testCase = this.randShowcase();

        // 검증을 위해 기존 항목 삭제.
        this.displayService.deleteShowcase(testCase.getProductId());

        // 새로 생성
        ShowcaseDto actual = this.displayService.createShowcase(testCase.getProductId(), testCase.getCategoryId());
        assertNotNull(actual);
        assertEquals(testCase.getProductId(), actual.getProductId());
        assertEquals(testCase.getCategoryId(), actual.getCategoryId());
    }

    @Test
    @Transactional
    void updateShowcase() {
        ShowcaseDto target = this.randShowcase();
        int expectedCategoryId = this.randShowcase().getCategoryId();
        while(expectedCategoryId == target.getCategoryId()) {
            expectedCategoryId = this.randShowcase().getCategoryId();
        }

        Optional<ShowcaseDto> optActual = this.displayService.updateShowcase(target.getProductId(), expectedCategoryId);
        assertTrue(optActual.isPresent());
        ShowcaseDto actual = optActual.get();
        assertEquals(target.getProductId(), actual.getProductId());
        assertEquals(expectedCategoryId, actual.getCategoryId());
    }

    @Test
    @Transactional
    void deleteShowcase() {
        ShowcaseDto target = this.randShowcase();
        this.displayService.deleteShowcase(target.getProductId());
    }

}