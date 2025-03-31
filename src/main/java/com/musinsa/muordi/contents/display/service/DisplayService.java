package com.musinsa.muordi.contents.display.service;

import com.musinsa.muordi.contents.display.dto.CategoryDto;
import com.musinsa.muordi.contents.display.dto.ShowcaseDto;
import com.musinsa.muordi.contents.display.repository.*;
import com.musinsa.muordi.platform.admin.repository.Product;
import com.musinsa.muordi.platform.admin.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Display 도메인 서비스.
 */
@RequiredArgsConstructor
@Service
public class DisplayService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ShowcaseRepository showcaseRepository;

    /**
     * 카테고리 목록을 조회한다. 조회결과는 항상 전시순서 오름차순으로 정렬되어있다.
     *
     * @return 카테고리 DTO 리스트를 반환한다.
     */
    public List<CategoryDto> getCategories() {
        return CategoryDto.fromEntities(this.categoryRepository.findAll());
    }

    /**
     * 쇼케이스 전체를 조회한다.
     * @return 쇼케이스 DTO 리스트
     */
    public List<ShowcaseDto> getShowcases() {
        return ShowcaseDto.fromEntities(this.showcaseRepository.findAll());
    }

    /**
     * 상품 식별자로 쇼케이스를 조회한다.
     * @param productId 상품 식별자.
     * @return 해당 상품의 쇼케이스 DTO를 감싼, 또는 빈 Optional을 반환한다.
     */
    public Optional<ShowcaseDto> getShowcase(long productId) {
        return this.showcaseRepository.findByProductId(productId).flatMap(entity -> Optional.of(ShowcaseDto.fromEntity(entity)));
    }

    /**
     * 카테고리 식별자로 쇼케이스를 조회한다.
     * @param categoryId 카테고리 식별자.
     * @return 해당 상품의 쇼케이스 DTO를 감싼, 또는 빈 Optional을 반환한다.
     */
    public List<ShowcaseDto> getShowcasesByCategoryId(int categoryId) {
        return ShowcaseDto.fromEntities(this.showcaseRepository.findByCategoryId(categoryId));
    }

    /**
     * 카테고리 명칭으로 쇼케이스를 조회한다.
     * @param categoryName 카테고리 명칭.
     * @return 해당 상품의 쇼케이스 DTO를 감싼, 또는 빈 Optional을 반환한다.
     */
    public List<ShowcaseDto> getShowcasesByCategoryName(String categoryName) {
        return ShowcaseDto.fromEntities(this.showcaseRepository.findByCategoryName(categoryName));
    }

    /**
     * 식별자로 쇼케이스를 조회한다.
     * @param brandId 브랜드 식별자.
     * @return 해당 상품의 쇼케이스 DTO를 감싼, 또는 빈 Optional을 반환한다.
     */
    public List<ShowcaseDto> getShowcasesByBrandId(int brandId) {
        return ShowcaseDto.fromEntities(this.showcaseRepository.findByBrandId(brandId));
    }

    /**
     * 식별자로 쇼케이스를 조회한다.
     * @param brandName 브랜드 이름.
     * @return 해당 상품의 쇼케이스 DTO를 감싼, 또는 빈 Optional을 반환한다.
     */
    public List<ShowcaseDto> getShowcasesByBrandName(String brandName) {
        return ShowcaseDto.fromEntities(this.showcaseRepository.findByBrandName(brandName));
    }

    // TODO 카테고리, 브랜드, 상품 존재 여부 1차 검증 & 디비 오류시 어떻게 응답할지.

    /**
     * 새로운 쇼케이스를 생성한다. 생성 전 해당 상품이 쇼케이스에 이미 등록되어있는지를 먼저 확인 후 없는 경우에 실행을 하지만,
     * 존재하지 않는 레코드에 대한 원자적 수행이 불가하므로 반드시 생성을 보장하지는 않는다.
     * 만약 동일한 상품에 대하여 동시에 쇼케이스 생성 요청이 들어오는 경우 하나의 요청은 성공하며, 나머지 요청은
     * 상품에 대한 유일키 제약에 의해 정합성 오류 예외를 반환한다.
     * @param productId 상품 식별자.
     * @param categoryId 카테고리 식별자.
     * @return 생성한 쇼케이스 DTO
     * TODO @throws 기술.
     */
    @Transactional
    public ShowcaseDto createShowcase(long productId, int categoryId) {
        // 상품이 이미 등록되어있는지를 확인한다.
        if (this.showcaseRepository.findByProductId(productId).isPresent()) {
            new RuntimeException("Showcase already exists");
        }

        // 상품 존재 여부를 검증한다.
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new RuntimeException(productId + " not found"));

        // 카테고리 존재 여부를 검증한다.
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException(categoryId + " not found"));

        // 쇼케이스를 생성하고 DTO를 반환한다.
        return ShowcaseDto.fromEntity(this.showcaseRepository.save(new Showcase(product, category)));
    }

    /**
     * 쇼케이스에 등록한 상품의 카테고리를 변경한다. 반드시 상품이 존재해야한다. 이 작업은 원자적으로 수행하므로 정합성을 보장한다.
     * @param productId 수정할 상품 식별자.
     * @param categoryId 변경할 카테고리 식별자.
     * @return 쇼케이스 DTO
     */
    @Transactional
    public Optional<ShowcaseDto> updateShowcase(long productId, int categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException(categoryId + " not found"));
        return this.showcaseRepository.updateByProductId(productId, Showcase.builder().category(category).build()).flatMap(entity -> Optional.of(ShowcaseDto.fromEntity(entity)));
    }

    /**
     * 상품 식별자로 쇼케이스를 지운다.
     * @param productId 상품 식별자
     */
    @Transactional
    public void deleteShowcase(long productId) {
        this.showcaseRepository.deleteByProductId(productId);
    }
}