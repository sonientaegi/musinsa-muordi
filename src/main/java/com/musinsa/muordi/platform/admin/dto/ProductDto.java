package com.musinsa.muordi.platform.admin.dto;

import com.musinsa.muordi.platform.admin.repository.Product;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * 상품 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         id : 상품 ID
 *     </li>
 *     <li>
 *         brandId : 브랜드 ID
 *     </li>
 *     <li>
 *         brandName : 브랜드 이름
 *     </li>
 *     <li>
 *         price : 상품 가격
 *     </li>
 * </ul>
 */
@Hidden
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private int brandId;
    private String brandName;
    private int price;

    @Override
    public ProductDto clone() {
        return new ProductDto(this.id, this.brandId, this.brandName, this.price);
    }
}
