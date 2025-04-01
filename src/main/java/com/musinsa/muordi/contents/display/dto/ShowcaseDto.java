package com.musinsa.muordi.contents.display.dto;

import com.musinsa.muordi.contents.display.repository.Showcase;
import lombok.*;

import java.util.List;

/**
 * 쇼케이스 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         productId : 상품 ID
 *     </li>
 *     <li>
 *         price : 상품 가격
 *     </li>
 *     <li>
 *         categoryId : 전시 카테고리 ID
 *     </li>
 *     <li>
 *         categoryName : 전시 카테고리 이름
 *     </li>
 *     <li>
 *         brandId : 브랜드 ID
 *     </li>
 *     <li>
 *         brandName : 브랜드 이름
 *     </li>
 * </ul>
 */
@Getter
@Setter
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new ShowcaseDto(this.productId, this.price, this.categoryId, this.categoryName, this.brandId, this.brandName);
    }
}