package com.musinsa.muordi.platform.domain;

import com.musinsa.muordi.platform.domain.product.Product;
import com.musinsa.muordi.platform.domain.product.ProductRepository;
import com.musinsa.muordi.platform.domain.product.ProductRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    @Test
    @DisplayName("상품 DTO -> entity 변환")
    void toEntity() {
        Product product = ProductRepositoryTest.sample();

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
                ProductRepositoryTest.sample(),
                ProductRepositoryTest.sample(),
                ProductRepositoryTest.sample(),
                ProductRepositoryTest.sample()
        );

        List<ProductDto> prodcutDtos = ProductDto.fromEntities(products);


    }
}