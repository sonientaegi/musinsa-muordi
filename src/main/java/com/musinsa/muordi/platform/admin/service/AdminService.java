package com.musinsa.muordi.platform.admin.service;

import com.musinsa.muordi.common.exception.DummyException;
import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import com.musinsa.muordi.common.exception.ResourceNotFoundException;
import com.musinsa.muordi.platform.admin.dto.BrandDto;
import com.musinsa.muordi.platform.admin.dto.BrandDtoMapper;
import com.musinsa.muordi.platform.admin.dto.ProductDto;
import com.musinsa.muordi.platform.admin.dto.ProductDtoMapper;
import com.musinsa.muordi.platform.admin.repository.*;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {
    // Repository 접근
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    // entity - DTO 맵퍼
    private final BrandDtoMapper brandMapper = BrandDtoMapper.instance;
    private final ProductDtoMapper productMapper = ProductDtoMapper.instance;

    /**
     * 전체 브랜드를 조회한다. 없으면 빈 리스트를 반환한다.
     * @return 브랜드 DTO 리스트. null 일 수 없다.
     */
    public List<BrandDto> getBrands() {
        return this.brandRepository.findAll().stream().map(this.brandMapper::fromEntity).toList();
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
     * 이름으로 브랜드를 조회한다. 같은 이름의 모든 브랜드를 조회하며, 하나도 없으면 빈 리스트를 반환한다.
     * @param name 조회할 브랜드 이름.
     * @return 브랜드 DTO 리스트. null일 수 없다.
     */
    public List<BrandDto> getBrands(String name) {
        return this.brandRepository.findByName(name).stream().map(this.brandMapper::fromEntity).toList();
    }

    /**
     * 새로운 브랜드를 생성한다. 만약 브랜드 생성에 실패했다면 예외를 반환한다.
     * @param brandDto 새로 생성할 브랜드 DTO.
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
     * @param id 삭제하려는 브랜드 ID.
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
     * 전체 상품을 조회한다. 없으면 빈 리스트를 반환한다.
     * @return 상품 DTO 리스트. null 일 수 없다.
     */
    public List<ProductDto> getProducts() {
        return this.productRepository.findAll().stream().map(this.productMapper::fromEntity).toList();
    }

    /**
     * 상품 ID를 조회한다. 상품 ID는 유일한 식별자이다.
     * @param id 상품 ID.
     * @return 상품 DTO. null 일 수 없다.
     * @throws ResourceNotFoundException 입력받은 ID의 상품이 존재하지 않는다.
     */
    public ProductDto getProduct(long id) {
        return this.productMapper.fromEntity(this.productRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    /**
     * @param brandId 브랜드 ID.
     * 브랜드 ID로 상품 목록을 조회한다. 하나의 브랜드를 여러 상품이 참조할 수 있다. 하나도 없으면 빈 리스트를 반환한다.
     * @return 상품 DTO 리스트. null 일 수 없다.
     */
    public List<ProductDto> getProductsByBrandId(int brandId) {
        return this.productRepository.findByBrandId(brandId).stream().map(this.productMapper::fromEntity).toList();
    }

    /**
     * 브랜드 이름으로 상품을 조회한다. 하나의 브랜드를 여러 상품이 참조할 수 있다. 하나도 없으면 빈 리스트를 반환한다.
     * @param brandName 브랜드 이름.
     * @return 상품 DTO 리스트. null 일 수 없다.
     */
    public List<ProductDto> getProductsByBrandName(String brandName) {
        return this.productRepository.findByBrandName(brandName).stream().map(this.productMapper::fromEntity).toList();
    }

    /**
     * 새로운 상품을 생성한다. 만약 상품 생성에 실패했따면 예외를 반환한다.
     * @param productDto 새로 생성할 상품 DTO.
     * @return 새로 생성한 상품 DTO.
     * @throws OptimisticLockingFailureException 락에 의해 생성 실패하였다.
     * @throws ResourceNotFoundException 참조하려는 브랜드가 존재하지 않는다.
     */
    public ProductDto createProduct(@NonNull ProductDto productDto) {
        // 브랜드 존재 여부 확인.
        int brandId = productDto.getBrandId();
        Brand brand = this.brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("BRAND ID=%d".formatted(brandId)));

        // 브랜드는 직접 주입해줘야한다.
        Product product = this.productMapper.toEntity(productDto, brand);

        // 상품을 저장한다. 위에서 브랜드의 존재 여부를 확인했지만, 동시에 들어온 요청에 의해 저장 전 브랜드가 삭제 될 수도 있다.
        // 이런 경우 DB의 정합성 제약 예외가 발생한다.
        // TODO 별도 예외 처리를 하지 않아도 데이터 정합성에 영향을 주지 않는다. 정보성을 위해 향후 검증 후 예외 명시 필요.
        return this.productMapper.fromEntity(this.productRepository.create(product));
    }

    /**
     * 상품을 수정한다. 만약 수정하려는 상품이 없거나 수정에 실패했다면 예외를 반환한다.
     * @param id 수정하려는 상품 ID.
     * @param productDto 수정하려는 상품 DTO.
     * @return 수정한 상품 DTO. null 일 수 없다.
     * @throws ResourceNotFoundException 수정하려는 상품이나, 참조하는 브랜드가 존재하지 않는다.
     */
    public ProductDto updateProduct(long id, @NonNull ProductDto productDto) {
        // 브랜드 존재 여부 확인.
        int brandId = productDto.getBrandId();
        Brand brand = this.brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("BRAND ID=%d".formatted(brandId)));

        // 브랜드는 직접 주입해줘야한다.
        Product product = this.productMapper.toEntity(productDto, brand);

        // 상품 업데인트.
        try {
            // TODO 브랜드 없는 경우 예외 처리 명시.
            return this.productMapper.fromEntity(this.productRepository.update(id, product));
        } catch (RepositoryEntityNotExistException e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * 상품을 삭제한다. 만약 삭제하려는 상품이 없거나 삭제에 실패했다면 예외를 반환한다.
     * @param id 삭제하려는 상품 ID.
     * @throws ResourceNotFoundException 삭제하려는 상품이 존재하지 않는다.
     */
    public void deleteProduct(long id) {
        try {
            this.productRepository.delete(id);
        } catch (RepositoryEntityNotExistException e) {
            throw new ResourceNotFoundException();
        }
    }
}
