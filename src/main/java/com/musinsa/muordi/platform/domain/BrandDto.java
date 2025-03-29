package com.musinsa.muordi.platform.domain;

import com.musinsa.muordi.platform.domain.brand.Brand;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 브랜드 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         id : 브랜드 식별자.
 *     </li>
 *     <li>
 *         name : 브랜드 이름.
 *     </li>
 * </ul>
 * 식별자는 읽기 전용이다. Repository로 읽어들인 경우에 한해 값을 가진다.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BrandDto {
    @Setter(AccessLevel.PACKAGE)
    private Integer id;
    private String name;

    /**
     * 새로운 브랜드 Dto를 생성한다. 식별자는 null 이다.
     * @param name
     */
    @Builder
    public BrandDto(String name) {
        this.name = name;
    }

    /**
     * 브랜드 Dto로 entity를 생성한다.
     * @return 브랜드 entity.
     */
    public Brand toEntity() {
        return Brand.builder().name(this.name).build();
    }

    /**
     * 브랜드 entity를 받아 DTO로 변환한다.
     * @param brand 브랜드 entity.
     * @return 브랜드 DTO.
     */
    public static BrandDto fromEntity(Brand brand) {
        BrandDto dto = new BrandDto();
        dto.id = brand.getId();
        dto.name = brand.getName();

        return dto;
    }

    /**
     * 브랜드 entity 리스트를 받아 DTO 리스트로 순서를 유지하여 변환한다. 브랜드 DTO와 리스트는 읽기 전용이다.
     * @param categories 브랜드 entity 리스트.
     * @return 브랜드 DTO 리스트.
     */
    public static List<BrandDto> fromEntities(List<Brand> categories) {
        return categories.stream().map(BrandDto::fromEntity).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected Object clone() {
        return new BrandDto(this.id, this.name);
    }
}
