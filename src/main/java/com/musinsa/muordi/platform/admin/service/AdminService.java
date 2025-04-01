package com.musinsa.muordi.platform.admin.service;

import com.musinsa.muordi.common.exception.BaseException;
import com.musinsa.muordi.common.exception.DummyException;
import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import com.musinsa.muordi.common.exception.ResourceNotFoundException;
import com.musinsa.muordi.platform.admin.dto.BrandDto;
import com.musinsa.muordi.platform.admin.dto.BrandDtoMapper;
import com.musinsa.muordi.platform.admin.dto.ProductDto;
import com.musinsa.muordi.platform.admin.repository.Brand;
import com.musinsa.muordi.platform.admin.repository.BrandRepository;
import com.musinsa.muordi.platform.admin.repository.Product;
import com.musinsa.muordi.platform.admin.repository.ProductRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {
    // Repository 접근
    private final BrandRepository brandRepository;
    private final ProductRepositoryImpl productRepositoryImpl;

    // entity - DTO 맵퍼
    private final BrandDtoMapper brandMapper = BrandDtoMapper.instance;

    /**
     * 전체 브랜드를 조회한다. 없으면 빈 리스트를 반환한다.
     * @return 브랜드 DTO 리스트. null일 수 없다.
     */
    public List<BrandDto> getBrands() {
        return this.brandRepository.findAll().stream().map(this.brandMapper::fromEntity).toList();
    }

    /**
     * 이름으로 브랜드를 조회한다. 같은 이름의 모든 브랜드를 조회하며, 하나도 없으면 빈 리스트를 반환한다.
     * @param name 조회할 브랜드 이름.
     * @return 브랜드 DTO 리스트. null일 수 없다.
     */
    public List<BrandDto> getBrands(String name) {
        return this.brandRepository.findByName(name).stream().map(this.brandMapper::fromEntity).toList();
    }

    /**
     * 브랜드 ID를 조회한다. 브랜드 ID는 유일한 식별자이다.
     * @param id 브랜드 ID.
     * @return 브랜드 DTO. null일 수 없다.
     * @throws ResourceNotFoundException 입력받은 ID의 브랜드가 존재하지 않는다.
     */
    public BrandDto getBrand(int id) {
        return this.brandMapper.fromEntity(this.brandRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    /**
     * 새로운 브랜드를 생성한다. 만약 브랜드 생성에 실패했다면 예외를 반환한다.
     * @param brandDto 브랜드 DTO.
     * @return 새로 생성한 브랜드 DTO.
     * @throws OptimisticLockingFailureException 락에 의해 생성 실패하였다.
     */
    public BrandDto createBrand(@NonNull BrandDto brandDto) {
        Brand expected = this.brandMapper.toEntity(brandDto);
        Brand actual = this.brandRepository.create(expected);
        return this.brandMapper.fromEntity(actual);
    }

    /**
     * 브랜드를 수정한다. 만약 수정하려는 브랜드가 없거나 수정에 실패했다면 예외를 반환한다.
     * @param id 수정하려는 브랜드 ID.
     * @param brandDto 수정하려는 브랜드 DTO.
     * @return 수정한 브랜드 DTO. null일 수 없다.
     * @throws ResourceNotFoundException 수정하려는 브랜드가 존재하지 않는다.
     */
    @Transactional
    public BrandDto updateBrand(int id, @NonNull BrandDto brandDto) {
        try {
            return this.brandMapper.fromEntity(this.brandRepository.update(id, this.brandMapper.toEntity(brandDto)));
        } catch (RepositoryEntityNotExistException e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * 브랜드를 삭제한다. 만약 삭제하려는 브랜드가 없거나 삭제에 실패했다면 예외를 반환한다.
     * @param id 삭제하려믄 브랜드 ID.
     * @throws ResourceNotFoundException 삭제하려는 브랜드가 존재하지 않는다.
     */
    public void deleteBrand(int id) {
        try {
            this.brandRepository.delete(id);
        } catch (RepositoryEntityNotExistException e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * 상품을 조회한다.
     * @param id 상품 식별자.
     * @return 해당 상품을 감싼, 그 외에는 비어있는 Optional을 반환한다.
     */
    public Optional<ProductDto> getProduct(long id) {
        return this.productRepositoryImpl.findById(id).flatMap(entity -> Optional.of(ProductDto.fromEntity(entity)));
    }

    /**
     * 상품 목록을 조회한다.
     * @return 상품 리스트
     */
    public List<ProductDto> getProducts() {
        return ProductDto.fromEntities(this.productRepositoryImpl.findAll());
    }

    /**
     * 브랜드 이름으로 상품목록을 조회한다. 같은 이름의 브랜드가 여러개 있으면 모두 반환한다.
     * @param brandName 브랜드 이름.
     * @return 상품 리스트
     */
    public List<ProductDto> getProductsByBrandName(String brandName) {
        return ProductDto.fromEntities(this.productRepositoryImpl.findByBrandName(brandName));
    }

    /**
     * 브랜드 식별자로 상품 목록을 조회한다.
     * @param brandId 브랜드 식별자.
     * @return 상품 리스트
     */
    public List<ProductDto> getProductsByBrandId(int brandId) {
        return ProductDto.fromEntities(this.productRepositoryImpl.findByBrandId(brandId));
    }

    // TODO 예외처리 어떻게 할지.
    // TODO 브랜드 없는 경우 검증 여부.
    /**
     * 새로운 상품을 생성한다.
     * @param productDto 새로 생성할 상품 DTO.
     * @return 새로 생성한 상품 DTO.
     */
    public ProductDto newProduct(@NonNull ProductDto productDto) {
        Optional<Brand> brand = this.brandRepository.findById(productDto.getBrandId());
        if (brand.isEmpty()) {
            // TODO 브랜드 없는 경우 어떻게 오류 처리할지 고민.
            throw new DummyException("Brand not found");
        }
        Product product = Product.builder()
                .brand(brand.get())
                .price(productDto.getPrice())
                .build();
        return ProductDto.fromEntity(productRepositoryImpl.save(product));
    }

    // TODO 예외처리 어떻게 할지.
    /**
     * 상품을 수정한다. 만약 수정하려는 상품이 없다면...
     * @param id 수정하려는 상품 식별자.
     * @param productDto 수정하려는 상품 정보.
     * @return 성공 시 수정한 상품을 감싼, 그 외에는 비어있는 Optional을 반환한다.
     */
    public Optional<ProductDto> updateProduct(long id, @NonNull ProductDto productDto) {
        Optional<Brand> brand = this.brandRepository.findById(productDto.getBrandId());
        if (brand.isEmpty()) {
            // TODO 브랜드 없는 경우 어떻게 오류 처리할지 고민.
           return Optional.empty();
        }
        Product product = Product.builder()
                .brand(brand.get())
                .price(productDto.getPrice())
                .build();
        return this.productRepositoryImpl.updateById(id, product).flatMap(entity -> Optional.of(ProductDto.fromEntity(entity)));
    }

    /**
     * 상품을 삭제한다.
     * @param id 삭제할 상품의 식별자.
     */
    public void deleteProduct(long id) {
        this.productRepositoryImpl.deleteById(id);
    }
}
