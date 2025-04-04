package com.musinsa.muordi.platform.admin.service;

import com.musinsa.muordi.common.exception.NotFoundException;
import com.musinsa.muordi.platform.admin.dto.BrandDto;
import com.musinsa.muordi.platform.admin.dto.BrandDtoMapper;
import com.musinsa.muordi.platform.admin.dto.ProductDto;
import com.musinsa.muordi.platform.admin.dto.ProductDtoMapper;
import com.musinsa.muordi.platform.admin.repository.Brand;
import com.musinsa.muordi.platform.admin.repository.BrandRepository;
import com.musinsa.muordi.platform.admin.repository.Product;
import com.musinsa.muordi.platform.admin.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 관리자 도메인 서비스이다.
 */
@RequiredArgsConstructor
@Service
public class AdminService {
    // Repository 접근
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    // entity - DTO 맵퍼
    private final BrandDtoMapper brandDtoMapper = BrandDtoMapper.instance;
    private final ProductDtoMapper productDtoMapper = ProductDtoMapper.instance;

    /**
     * 전체 브랜드를 조회한다. 없으면 빈 리스트를 반환한다.
     * @return 브랜드 DTO 리스트. null 일 수 없다.
     */
    public List<BrandDto> getBrands() {
        List<Brand> brands = this.brandRepository.findAll();

        List<BrandDto> brandDtos = new ArrayList<>();
        for (Brand brand : brands) {
            brandDtos.add(this.brandDtoMapper.fromEntity(brand));
        }
        return brandDtos;
    }

    /**
     * 브랜드 ID를 조회한다. 브랜드 ID는 유일한 식별자이다.
     * @param id 브랜드 ID.
     * @return 브랜드 DTO. null 일 수 없다.
     * @throws NotFoundException 입력받은 ID의 브랜드가 존재하지 않는다.
     */
    public BrandDto getBrand(int id) {
        return this.brandDtoMapper.fromEntity(this.brandRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    /**
     * 이름으로 브랜드를 조회한다. 같은 이름의 모든 브랜드를 조회하며, 하나도 없으면 빈 리스트를 반환한다.
     * @param name 조회할 브랜드 이름.
     * @return 브랜드 DTO 리스트. null일 수 없다.
     */
    public List<BrandDto> getBrands(String name) {
        return this.brandRepository.findByName(name).stream().map(this.brandDtoMapper::fromEntity).toList();
    }

    /**
     * 새로운 브랜드를 생성한다. 만약 브랜드 생성에 실패했다면 예외를 반환한다.
     * @param brandDto 새로 생성할 브랜드 DTO.
     * @return 새로 생성한 브랜드 DTO.
     * @throws OptimisticLockingFailureException 락에 의해 생성 실패하였다.
     */
    public BrandDto createBrand(@NonNull BrandDto brandDto) {
        Brand expected = this.brandDtoMapper.toEntity(brandDto);
        Brand actual = this.brandRepository.create(expected);
        return this.brandDtoMapper.fromEntity(actual);
    }

    /**
     * 브랜드를 수정한다. 만약 수정하려는 브랜드가 없거나 수정에 실패했다면 예외를 반환한다.
     * @param id 수정하려는 브랜드 ID.
     * @param brandDto 수정하려는 브랜드 DTO.
     * @return 수정한 브랜드 DTO. null일 수 없다.
     * @throws NotFoundException 수정하려는 브랜드가 존재하지 않는다.
     */
    @Transactional
    public BrandDto updateBrand(int id, @NonNull BrandDto brandDto) {
        this.brandRepository.findById(id).orElseThrow(NotFoundException::new);
        return this.brandDtoMapper.fromEntity(this.brandRepository.update(id, this.brandDtoMapper.toEntity(brandDto)));
    }

    /**
     * 브랜드를 삭제한다. 만약 삭제하려는 브랜드가 없거나 삭제에 실패했다면 예외를 반환한다.
     * @param id 삭제하려는 브랜드 ID.
     * @throws NotFoundException 삭제하려는 브랜드가 존재하지 않는다.
     */
    public void deleteBrand(int id) {
        this.brandRepository.findById(id).orElseThrow(NotFoundException::new);
        this.brandRepository.delete(id);
    }

    /**
     * 전체 상품을 조회한다. 없으면 빈 리스트를 반환한다.
     * @return 상품 DTO 리스트. null 일 수 없다.
     */
    public List<ProductDto> getProducts() {
        return this.productRepository.findAll().stream().map(this.productDtoMapper::fromEntity).toList();
    }

    /**
     * 상품 ID를 조회한다. 상품 ID는 유일한 식별자이다.
     * @param id 상품 ID.
     * @return 상품 DTO. null 일 수 없다.
     * @throws NotFoundException 입력받은 ID의 상품이 존재하지 않는다.
     */
    public ProductDto getProduct(long id) {
        return this.productDtoMapper.fromEntity(this.productRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    /**
     * @param brandId 브랜드 ID.
     * 브랜드 ID로 상품 목록을 조회한다. 하나의 브랜드를 여러 상품이 참조할 수 있다. 하나도 없으면 빈 리스트를 반환한다.
     * @return 상품 DTO 리스트. null 일 수 없다.
     */
    public List<ProductDto> getProductsByBrandId(int brandId) {
        return this.productRepository.findByBrandId(brandId).stream().map(this.productDtoMapper::fromEntity).toList();
    }

    /**
     * 브랜드 이름으로 상품을 조회한다. 하나의 브랜드를 여러 상품이 참조할 수 있다. 하나도 없으면 빈 리스트를 반환한다.
     * @param brandName 브랜드 이름.
     * @return 상품 DTO 리스트. null 일 수 없다.
     */
    public List<ProductDto> getProductsByBrandName(String brandName) {
        return this.productRepository.findByBrandName(brandName).stream().map(this.productDtoMapper::fromEntity).toList();
    }

    /**
     * 새로운 상품을 생성한다. 만약 상품 생성에 실패했따면 예외를 반환한다.
     * @param productDto 새로 생성할 상품 DTO.
     * @return 새로 생성한 상품 DTO.
     * @throws OptimisticLockingFailureException 락에 의해 생성 실패하였다.
     * @throws NotFoundException 참조하려는 브랜드가 존재하지 않는다.
     */
    public ProductDto createProduct(@NonNull ProductDto productDto) {
        // 브랜드 존재 여부 확인.
        int brandId = productDto.getBrandId();
        Brand brand = this.brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException("BRAND", brandId));

        // 브랜드는 직접 주입해줘야한다.
        Product product = this.productDtoMapper.toEntity(productDto, brand);

        // 상품을 저장한다. 위에서 브랜드의 존재 여부를 확인했지만, 동시에 들어온 요청에 의해 저장 전 브랜드가 삭제 될 수도 있다.
        // 이런 경우 DB의 정합성 제약 예외가 발생한다.
        // TODO 별도 예외 처리를 하지 않아도 데이터 정합성에 영향을 주지 않는다. 정보성을 위해 향후 검증 후 예외 명시 필요.
        return this.productDtoMapper.fromEntity(this.productRepository.create(product));
    }

    /**
     * 상품을 수정한다. 만약 수정하려는 상품이 없거나 수정에 실패했다면 예외를 반환한다.
     * @param id 수정하려는 상품 ID.
     * @param productDto 수정하려는 상품 DTO.
     * @return 수정한 상품 DTO. null 일 수 없다.
     * @throws NotFoundException 수정하려는 상품이나, 참조하는 브랜드가 존재하지 않는다.
     */
    public ProductDto updateProduct(long id, @NonNull ProductDto productDto) {
        // 상품 존재 여부 확인.
        this.productRepository.findById(id).orElseThrow(NotFoundException::new);

        // 브랜드 존재 여부 확인.
        int brandId = productDto.getBrandId();
        Brand brand = this.brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException("BRAND", brandId));

        // 브랜드는 직접 주입해줘야한다.
        Product product = this.productDtoMapper.toEntity(productDto, brand);

        return this.productDtoMapper.fromEntity(this.productRepository.update(id, product));
    }

    /**
     * 상품을 삭제한다. 만약 삭제하려는 상품이 없거나 삭제에 실패했다면 예외를 반환한다.
     * @param id 삭제하려는 상품 ID.
     * @throws NotFoundException 삭제하려는 상품이 존재하지 않는다.
     */
    public void deleteProduct(long id) {
        this.productRepository.findById(id).orElseThrow(NotFoundException::new);
        this.productRepository.delete(id);
    }
}
