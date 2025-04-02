package com.musinsa.muordi.contents.display.service;

import com.musinsa.muordi.common.exception.RepositoryEntityDuplicatedException;
import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import com.musinsa.muordi.common.exception.ResourceNotFoundException;
import com.musinsa.muordi.contents.display.dto.CategoryDto;
import com.musinsa.muordi.contents.display.dto.CategoryDtoMapper;
import com.musinsa.muordi.contents.display.dto.ShowcaseDto;
import com.musinsa.muordi.contents.display.dto.ShowcaseDtoMapper;
import com.musinsa.muordi.contents.display.repository.Category;
import com.musinsa.muordi.contents.display.repository.CategoryRepository;
import com.musinsa.muordi.contents.display.repository.Showcase;
import com.musinsa.muordi.contents.display.repository.ShowcaseRepository;
import com.musinsa.muordi.platform.admin.repository.Product;
import com.musinsa.muordi.platform.admin.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 전시 도메인 서비스.
 */
@RequiredArgsConstructor
@Service
public class DisplayService {
    // Repository 접근
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;      // <- 중간 서비스를 구성하여 의존성을 줄일 수 있다. 확인 필요.
    private final ShowcaseRepository showcaseRepository;

    // entity - DTO 맵퍼
    private final CategoryDtoMapper categoryDtoMapper = CategoryDtoMapper.instance;
    private final ShowcaseDtoMapper showcaseDtoMapper = ShowcaseDtoMapper.instance;

    /**
     * 카테고리 목록을 조회한다. 조회결과는 항상 전시순서 오름차순으로 정렬되어있다.
     * @return 카테고리 DTO 리스트. null 일 수 없다.
     */
    public List<CategoryDto> getCategories() {
        // 카테고리는 정렬 순서 보장 필요.
        List<CategoryDto> categories = new ArrayList<>();
        this.categoryRepository.findAll().forEach(category -> categories.add(this.categoryDtoMapper.fromEntity(category)));
        return categories;
    }

    /**
     * 카테고리 ID를 조회한다. 카테고리 ID는 유일한 식별자이다.
     * @param id 카테고리 ID.
     * @return 카테고리 DTO. null 일 수 없다.
     * @throws ResourceNotFoundException 입력받은 ID의 카테고리가 존재하지 않는다.
     */
    public CategoryDto getCategory(int id) {
        return this.categoryDtoMapper.fromEntity(this.categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    /**
     * 카테고리 이름으로 카테고리를 조회한다. 만약 같은 이름의 카테고리가 여러개 있다면 전시순사가 가장 높은 하나를 반환한다.
     * @param name 카테고리 이름.
     * @return 카테고리 DTO. null 일 수 없다.
     * @throws ResourceNotFoundException 입력받은 이름의 카테고리가 존재하지 않는다.
     */
    public CategoryDto getCategoryByName(String name) {
        List<Category> categories = this.categoryRepository.findByName(name);
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return this.categoryDtoMapper.fromEntity(categories.get(0));
        }
    }

    /**
     * 전체 쇼케이스를 조회한다. 없으면 빈 리스트를 반환한다.
     * @return 쇼케이스 DTO 리스트. null 일 수 없다.
     */
    public List<ShowcaseDto> getShowcases() {
        return this.showcaseRepository.findAll().stream().map(showcaseDtoMapper::fromEntity).toList();
    }

    /**
     * 상품의 쇼케이스 정보를 반환한다.
     * @param productId 상품 ID.
     * @return 쇼케이스 DTO. null 일 수 없다.
     * @throws ResourceNotFoundException 입력받은 상품의 쇼케이스 정보가 없다.
     */
    public ShowcaseDto getShowcase(long productId) {
        return this.showcaseDtoMapper.fromEntity(this.showcaseRepository.findByProductId(productId).orElseThrow(ResourceNotFoundException::new));
    }

    /**
     * 지정한 카테고리로 등록한 모든 쇼케이스를 반환한다.
     * @param categoryId 카테고리 ID.
     * @return 쇼케이스 DTO 리스트. null 일 수 없다.
     */
    public List<ShowcaseDto> getShowcasesByCategoryId(int categoryId) {
        return this.showcaseRepository.findByCategoryId(categoryId).stream().map(showcaseDtoMapper::fromEntity).toList();
    }

    /**
     * 지정한 카테고리로 등록한 모든 쇼케이스를 반환한다.
     * @param categoryName 카테고리 이름.
     * @return 쇼케이스 DTO 리스트. null 일 수 없다.
     */
    public List<ShowcaseDto> getShowcasesByCategoryName(String categoryName) {
        return this.showcaseRepository.findByCategoryName(categoryName).stream().map(showcaseDtoMapper::fromEntity).toList();
    }

    /**
     * 지정한 브랜드로 등록한 모든 쇼케이스를 반환한다.
     * @param brandId 브랜드 ID.
     * @return 쇼케이스 DTO 리스트. null 일 수 없다.
     */
    public List<ShowcaseDto> getShowcasesByBrandId(int brandId) {
        return this.showcaseRepository.findByBrandId(brandId).stream().map(showcaseDtoMapper::fromEntity).toList();
    }

    /**
     * 지정한 브랜드로 등록한 모든 쇼케이스를 반환한다.
     * @param brandName 브랜드 이름.
     * @return 쇼케이스 DTO 리스트. null 일 수 없다.
     */
    public List<ShowcaseDto> getShowcasesByBrandName(String brandName) {
        return this.showcaseRepository.findByBrandName(brandName).stream().map(showcaseDtoMapper::fromEntity).toList();
    }

    /**
     * 새로운 쇼케이스를 생성한다. 생성 전 해당 상품이 쇼케이스에 이미 등록되어있는지를 먼저 확인 후 없는 경우에 실행을 하지만,
     * 존재하지 않는 레코드에 대한 원자적 수행이 불가하므로 반드시 생성을 보장하지는 않는다.
     * 만약 동일한 상품에 대하여 동시에 쇼케이스 생성 요청이 들어오는 경우 하나의 요청은 성공하며, 나머지 요청은
     * 상품에 대한 유일키 제약에 의해 정합성 오류 예외를 반환한다.
     * @param productId 상품 ID.
     * @param categoryId 카테고리 ID.
     * @return 생성한 쇼케이스 DTO. null 일 수 없다.
     * @throws RepositoryEntityNotExistException 해당 상품이 쇼케이스에 등록되어있다.
     * @throws ResourceNotFoundException 해당 상품이 존재하지 않는다.
     */
    @Transactional
    public ShowcaseDto createShowcase(long productId, int categoryId) {
        // 상품이 이미 등록되어있는지를 확인한다.
        if (this.showcaseRepository.findByProductId(productId).isPresent()) {
            throw new RepositoryEntityDuplicatedException("SHOWCASE", productId);
        }

        // 상품 존재 여부를 검증한다.
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("PRODUCT=%d".formatted(productId)));

        // 카테고리 존재 여부를 검증한다.
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("CATEGORY=%d".formatted(categoryId)));

        // 쇼케이스를 생성하고 DTO를 반환한다.
        return this.showcaseDtoMapper.fromEntity(this.showcaseRepository.create(this.showcaseDtoMapper.toEntity(product, category)));
    }

    /**
     * 쇼케이스에 등록한 상품의 카테고리를 변경한다. 반드시 상품이 존재해야한다. 이 작업은 원자적으로 수행하므로 정합성을 보장한다.
     * @param productId 수정하고 싶은 상품 ID.
     * @param categoryId 지정하려는 카테고리 ID.
     * @return 수정한 쇼케이스 DTO. null 일 수 없다.
     * @throws ResourceNotFoundException 변경하려는 카테고리나 상품이 존재하지 않는다.
     */
    @Transactional
    public ShowcaseDto updateShowcase(long productId, int categoryId) {
        // 상품 존재 여부를 검증한다.
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("PRODUCT=%d".formatted(productId)));

        // 카테고리 존재 여부를 검증한다.
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("CATEGORY=%d".formatted(categoryId)));

        // 수정을 위한 entity를 생성한다.
        Showcase showcase = this.showcaseDtoMapper.toEntity(product, category);

        // 쇼케이스 수정.
        return this.showcaseDtoMapper.fromEntity(this.showcaseRepository.update(showcase));
    }

    /**
     * 상품 식별자로 쇼케이스를 지운다.
     * @param productId 상품 식별자
     */
    @Transactional
    public void deleteShowcase(long productId) {
        this.showcaseRepository.delete(productId);
    }
}