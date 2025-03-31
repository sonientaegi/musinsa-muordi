package com.musinsa.muordi.contents.display.dto;

import com.musinsa.muordi.contents.display.repository.Showcase;
import lombok.*;

import java.util.List;

/**
 * 쇼케이스 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>productId : 상품 식별자</li>
 *     <li>categoryId : 전시 카테고리</li>
 * </ul>
 * 쇼케이스의 더미 PK는 사용하지 않으며, 유니크 키인 상품식별자가 PK를 갈음한다.
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ShowcaseDto {
    private Long productId;
    private Integer price;
    private Integer categoryId;
    private String categoryName;
    private Integer brandId;
    private String brandName;

    /**
     * 쇼케이스 entity를 받아 DTO로 변환한다.
     * @param showcase 쇼케이스 entity.
     * @return 쇼케이스 DTO.
     */
    public static ShowcaseDto fromEntity(Showcase showcase) {
        ShowcaseDto showcaseDto = new ShowcaseDto();
//        showcaseDto.productId = showcase.getProduct().getId();
//        showcaseDto.price = showcase.getProduct().getPrice();
//        showcaseDto.categoryId = showcase.getCategory().getId();
//        showcaseDto.categoryName = showcase.getCategory().getName();
//        showcaseDto.brandId = showcase.getProduct().getBrand().getId();
//        showcaseDto.brandName = showcase.getProduct().getBrand().getName();
//
        return showcaseDto;
    }

    /**
     * 쇼케이스 entity 리스트를 받아 DTO 리스트로 변환한다.
     *
     * @return 쇼케이스 DTO.
     */
    public static List<ShowcaseDto> fromEntities(List<Showcase> showcases) {
        return showcases.stream().map(ShowcaseDto::fromEntity).toList();
    }
}