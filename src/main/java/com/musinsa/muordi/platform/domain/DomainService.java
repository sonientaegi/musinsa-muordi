package com.musinsa.muordi.platform.domain;

import com.musinsa.muordi.platform.domain.brand.Brand;
import com.musinsa.muordi.platform.domain.brand.BrandRepository;
import com.musinsa.muordi.platform.domain.category.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DomainService {
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    /**
     * 카테고리 목록을 조회한다. 조회결과는 항상 전시순서 오름차순으로 정렬되어있다.
     * @return 카테고리 DTO 리스트를 반환한다.
     */
    public List<CategoryDto> getCategories() {
        return CategoryDto.fromEntities(this.categoryRepository.findAll());
    }

    /**
     * 브랜드 목록을 조회한다.
     * @return 모든 브랜드 리스트 또는 빈 리스트를 반환한다.
     */
    public List<BrandDto> getBrands() {
        return BrandDto.fromEntities(this.brandRepository.findAll());
    }

    /**
     * 브랜드 이름을 조회한다. 동일한 이름을 가진 브랜드가 있는 경우 모두 반환한다.
     * @param name 브랜드 이름
     * @return 발견한 브랜드 리스트, 또는 빈 리스트를 반환한다.
     */
    public List<BrandDto> getBrands(String name) {
        return BrandDto.fromEntities(this.brandRepository.findByName(name));
    }

    /**
     * 브랜드 식별자를 조회한다.
     * @param id
     * @return 해당 식별자의 브랜드가 존재하는 경우 해당 브랜드를 감싼, 그 외에는 비어있는 Optional을 반환한다.
     */
    public Optional<BrandDto> getBrand(int id) {
        return this.brandRepository.findById(id).flatMap(entity -> Optional.of(BrandDto.fromEntity(entity)));
    }

    /**
     * 새로운 브랜드를 생성한다.
     * @param brandDto 새로 생성 할 브랜드 DTO.
     * @return 새로 생성한 브랜드 DTO.
     * @throws DomainException 브랜드 생성 실패시 발생하는 예외이다.
     */
    public BrandDto newBrand(@NonNull BrandDto brandDto) {
        Brand src = brandDto.toEntity();
        try {
            // 신규생성은 DB 오류를 제외하면 반드시 성공해야한다.
            // BrandDto의 id는 외부 패키지에서는 읽기 전용이므로, 임의로 쓰기 대신 수정을 일으킬 수는 없다.
            // 따라서 save 트랜잭션은 반드시 entity를 반환하거나, 예외를 일으킨다.
            return BrandDto.fromEntity(this.brandRepository.save(src));
        } catch (OptimisticLockingFailureException e) {
            e.printStackTrace();
            throw new DomainException(e);
        }
    }

    /**
     * 브랜드를 수정한다. 만약 수정하려는 브랜드가 없다면 빈 Optional을 반환한다.
     * @param id 수정하려는 브랜드 식별자
     * @param brandDto 수정하려는 브랜드 정보. 식별자를 제외한 모든 값을 수정한다.
     * @return 성공시 수정한 브랜드를 감싼, 그 외에는 비어있는 Optional을 반환한다.
     * @throws DomainException 브랜드 수정 실패시 발생하는 예외이다.
     */
    @Transactional
    public Optional<BrandDto> updateBrand(int id, @NonNull BrandDto brandDto) {
        Brand src = brandDto.toEntity();
        try {
            // 수정은 배타적 읽기 -> 쓰기 순으로 진행한다. 만약 레코드가 없다면 빈 Optional을, 수정에 성공하면 수정한 레코드를 포함하는 Optional을 반환한다.
            // 만약 락 획득 실패, DB오류등의 경우에는 예외를 반환한다.
            // 배타적 락이 없다면...
            // - 읽고(A) -> 지우고(B) -> 수정(A) -> True negative
            // - 읽고(A) -> 지우고(B) -> 생성(A) -> False Positive
            // 의 경우가 발생할 수 있다.
            return this.brandRepository.updateById(id, src).flatMap(entity -> Optional.of(BrandDto.fromEntity(entity)));
        } catch (OptimisticLockingFailureException e) {
            e.printStackTrace();
            throw new DomainException(e);
        }
    }

    /**
     * 브랜드를 삭제한다.
     * @param id 삭제할 브랜드 식별자
     */
    public void deleteBrand(int id) {
        this.brandRepository.deleteById(id);
    }
}
