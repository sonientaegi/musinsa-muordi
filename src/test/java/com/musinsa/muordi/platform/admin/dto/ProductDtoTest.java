package com.musinsa.muordi.platform.admin.dto;

import com.musinsa.muordi.platform.admin.repository.Product;
import com.musinsa.muordi.platform.admin.repository.ProductRepositoryJpaWrapperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    @Test
    @DisplayName("상품 DTO -> entity 변환")
    void toEntity() {
        Product product = ProductRepositoryJpaWrapperTest.sample();

        ProductDto productDto = ProductDto.fromEntity(product);
        assertEquals(product.getId(), productDto.getId());
        assertEquals(product.getPrice(), productDto.getPrice());
        assertEquals(product.getBrand().getId(), productDto.getBrandId());
        assertEquals(product.getBrand().getName(), productDto.getBrandName());
    }

    @Test
    @DisplayName("상품 entity -> DTO 변환")
    void fromEntity() {
        List<Product> products = List.of(
                ProductRepositoryJpaWrapperTest.sample(),
                ProductRepositoryJpaWrapperTest.sample(),
                ProductRepositoryJpaWrapperTest.sample(),
                ProductRepositoryJpaWrapperTest.sample()
        );

        List<ProductDto> productDtos = ProductDto.fromEntities(products);
        IntStream.range(0, products.size()).forEach(i -> {
            Product entity = products.get(i);
            ProductDto dto = productDtos.get(i);
            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getPrice(), dto.getPrice());
            assertEquals(entity.getBrand().getId(), dto.getBrandId());
            assertEquals(entity.getBrand().getName(), dto.getBrandName());
        });
    }
}