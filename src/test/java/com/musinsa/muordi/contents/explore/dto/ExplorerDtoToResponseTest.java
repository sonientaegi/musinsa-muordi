package com.musinsa.muordi.contents.explore.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.muordi.contents.explore.repository.ExploreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExplorerDtoToResponseTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("카테고리 별 최저가격 브랜드 응답")
    void testCategoryBrandCheapestResponse() {
        CategoryBrandCheapestDto dto = CategoryBrandCheapestDto.builder()
                .totalAmount(34100)
                .priceRecords(List.of(
                        ItemNamedDto.builder().categoryName("상의").brandName("C").categoryId(1).brandId(3).price(10000).build(),
                        ItemNamedDto.builder().categoryName("아우터").brandName("E").categoryId(2).brandId(5).price(5000).build(),
                        ItemNamedDto.builder().categoryName("바지").brandName("D").categoryId(3).brandId(4).price(3000).build(),
                        ItemNamedDto.builder().categoryName("스니커즈").brandName("G").categoryId(4).brandId(7).price(9000).build(),
                        ItemNamedDto.builder().categoryName("가방").brandName("A").categoryId(5).brandId(1).price(2000).build(),
                        ItemNamedDto.builder().categoryName("모자").brandName("D").categoryId(6).brandId(4).price(1500).build(),
                        ItemNamedDto.builder().categoryName("양말").brandName("I").categoryId(7).brandId(9).price(1700).build(),
                        ItemNamedDto.builder().categoryName("액세서리").brandName("F").categoryId(8).brandId(6).price(1900).build()
                ))
                .build();

        CategoryBrandCheapestReponse response = CategoryBrandCheapestReponse.fromDto(dto);
        String[] jsonString = {""};
        assertDoesNotThrow(() -> jsonString[0] = objectMapper.writeValueAsString(response));
        String actual = jsonString[0];
        String expected = """
                {"총액":"34,100","최저가":[{"카테고리":"상의","브랜드":"C","가격":"10,000"},{"카테고리":"아우터","브랜드":"E","가격":"5,000"},{"카테고리":"바지","브랜드":"D","가격":"3,000"},{"카테고리":"스니커즈","브랜드":"G","가격":"9,000"},{"카테고리":"가방","브랜드":"A","가격":"2,000"},{"카테고리":"모자","브랜드":"D","가격":"1,500"},{"카테고리":"양말","브랜드":"I","가격":"1,700"},{"카테고리":"액세서리","브랜드":"F","가격":"1,900"}]}""";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("최저 가격 브랜드 응답")
    void testBrandCheapestResponse() {
        BrandCheapestDto dto = BrandCheapestDto.builder()
                .brandName("D")
                .priceRecords(List.of(
                        ItemNamedDto.builder().categoryName("상의").brandName("C").categoryId(1).brandId(3).price(10000).build(),
                        ItemNamedDto.builder().categoryName("아우터").brandName("E").categoryId(2).brandId(5).price(5000).build(),
                        ItemNamedDto.builder().categoryName("바지").brandName("D").categoryId(3).brandId(4).price(3000).build(),
                        ItemNamedDto.builder().categoryName("스니커즈").brandName("G").categoryId(4).brandId(7).price(9000).build(),
                        ItemNamedDto.builder().categoryName("가방").brandName("A").categoryId(5).brandId(1).price(2000).build(),
                        ItemNamedDto.builder().categoryName("모자").brandName("D").categoryId(6).brandId(4).price(1500).build(),
                        ItemNamedDto.builder().categoryName("양말").brandName("I").categoryId(7).brandId(9).price(1700).build(),
                        ItemNamedDto.builder().categoryName("액세서리").brandName("F").categoryId(8).brandId(6).price(1900).build()
                ))
                .totalAmount(36100)
                .build();

        BrandCheapestResponse response = BrandCheapestResponse.fromDto(dto);
        String[] jsonString = {""};
        assertDoesNotThrow(() -> jsonString[0] = objectMapper.writeValueAsString(response));
        String actual = jsonString[0];
        String expected = """
                {"최저가":{"브랜드":"D","카테고리":[{"카테고리":"상의","가격":"10,000"},{"카테고리":"아우터","가격":"5,000"},{"카테고리":"바지","가격":"3,000"},{"카테고리":"스니커즈","가격":"9,000"},{"카테고리":"가방","가격":"2,000"},{"카테고리":"모자","가격":"1,500"},{"카테고리":"양말","가격":"1,700"},{"카테고리":"액세서리","가격":"1,900"}],"총액":"36,100"}}""";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("카테고리 가격대 응답")
    void testCategoryPriceRangeResponse() {
        CategoryPriceRangeDto dto = CategoryPriceRangeDto.builder()
                .categoryName("상의")
                .minPriceRecord(ItemNamedDto.builder().brandName("C").categoryName("상의").price(10000).build())
                .maxPriceRecord(ItemNamedDto.builder().brandName("I").categoryName("상의").price(11400).build())
                .build();
        CategoryPriceRangeResponse response = CategoryPriceRangeResponse.fromDto(dto);
        String[] jsonString = {""};
        assertDoesNotThrow(() -> jsonString[0] = objectMapper.writeValueAsString(response));
        String actual = jsonString[0];
        String expected = """
                {"카테고리":"상의","최저가":[{"브랜드":"C","가격":"10,000"}],"최고가":[{"브랜드":"I","가격":"11,400"}]}""";
        assertEquals(expected, actual);
    }
}