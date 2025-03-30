package com.musinsa.muordi.platform.domain;

import com.musinsa.muordi.platform.domain.product.Product;
import lombok.*;

import java.util.List;

/**
 * 상품 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>id : 상품 식별자</li>
 *     <li>brandId : 브랜드 식별자</li>
 *     <li>brandName : 브랜드 이름</li>
 *     <li>price : 가격</li>
 * </ul>
 * 상품식별자와 브랜드 정보는 읽기 전용이며, 판매가 필드는 수정이 가능하다. 신규 상품 생성시에는 가격정보를 입력한 DTO를 이용한다.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProductDto {
    @Setter(AccessLevel.PACKAGE)
    private Long id;

    private int brandId;

    @Setter(AccessLevel.PACKAGE)
    private String brandName;

    private Integer price;

    /**
     * 신규 상품 등록용 객체 생성자.
     * @param brandId 브랜드 식별자
     * @param price 판매가
     */
    @Builder
    public ProductDto(int brandId, int price) {
        this.brandId = brandId;
        this.brandName = null;
        this.price = price;
    }

    /**
     * 브랜드 식별자를 설정한다. entity를 참조하여 생헝한 Dto는 브랜드 이름을 가지고 있지만, 그 밖의 경우에 Dto를 생성하거나 브랜드 식별자를 수정하는경우
     * 브랜드 명칭은 null로 바뀐다.
     * @param brandId
     */
    public void setBrandId(int brandId) {
        this.brandId = brandId;
        this.setBrandName(null);
    }

    /**
     * 상품 entity를 받아 DTO로 변환한다.
     * @param product 상품 entity
     * @return 상품 DTO
     */
    public static ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.id = product.getId();
        // FK 정보가 있는 경우에만 복사.
        if (product.getBrand() != null) {
            dto.brandId = product.getBrand().getId();
            dto.brandName = product.getBrand().getName();
        }
        dto.price = product.getPrice();

        return dto;
    }

    /**
     * 상품 entity 리스트를 받아 DTO 리스트로 순서를 유지하여 변환한다. 상품 DTO 리스트는 읽기 전용이다.
     * @param products 상품 entity 리스트.
     * @return 상품 DTO 리스트.
     */
    public static List<ProductDto> fromEntities(List<Product> products) {
        return products.stream().map(ProductDto::fromEntity).toList();
    }

    @Override
    public Object clone() {
        return new ProductDto(this.id, this.brandId, this.brandName, this.price);
    }
}
