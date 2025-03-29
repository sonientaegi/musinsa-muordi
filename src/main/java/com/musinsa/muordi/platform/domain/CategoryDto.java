package com.musinsa.muordi.platform.domain;

import com.musinsa.muordi.platform.domain.category.Category;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 카테고리 DTO는 다음의 정보를 제공한다.
 * <ul>
 *     <li>
 *         id : 카테고리 식별자.
 *     </li>
 *     <li>
 *         name : 카테고리 이름.
 *     </li>
 *     <li>
 *         displaySequence : 카테고리 정렬 순서.
 *     </li>
 * </ul>
 * 카테고리는 읽기 전용이다.
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CategoryDto {
    private Integer id;
    private String name;
    private int displaySequence;

    /**
     * 카테고리 entity를 받아 DTO로 변환한다. 카테고리 DTO는 읽기 전용이다.
     * @param category 카테고리 entity.
     * @return 카테고리 DTO.
     */
    public static CategoryDto fromEntity(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.id = category.getId();
        dto.name = category.getName();
        dto.displaySequence = category.getDisplaySequence();

        return dto;
    }

    /**
     * 카테고리 entity 리스트를 받아 DTO 리스트로 순서를 유지하여 변환한다. 카테고리 DTO와 리스트는 읽기 전용이다.
     * @param categories 카테고리 entity 리스트.
     * @return 카테고리 DTO 리스트.
     */
    public static List<CategoryDto> fromEntities(List<Category> categories) {
        return categories.stream().map(CategoryDto::fromEntity).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected Object clone() {
        CategoryDto dst = new CategoryDto();
        dst.id = this.id;
        dst.name = this.name;
        dst.displaySequence = this.displaySequence;

        return dst;
    }
}
