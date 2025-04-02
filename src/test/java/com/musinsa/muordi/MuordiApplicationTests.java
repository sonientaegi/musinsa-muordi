package com.musinsa.muordi;

import com.musinsa.muordi.contents.display.repository.CategoryRepositoryJpa;
import com.musinsa.muordi.platform.admin.repository.BrandRepository;
import com.musinsa.muordi.platform.admin.repository.ProductRepositoryJpaWrapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MuordiApplicationTests {
    @Autowired
    EntityManager em;

    @Autowired
    CategoryRepositoryJpa categoryRepositoryJpa;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ProductRepositoryJpaWrapper productRepositoryJpaWrapper;

    @Test
    void contextLoads() {
    }

    @Test
    void Text() {
//        List<BrandDTO> brands = this.brandRepository.saveAll(List.of(
//                new BrandDTO("BRAND 1"),
//                new BrandDTO("BRAND 2"))
//        );
//        List<ProductDTO> products = this.productRepositoryImpl.saveAll(List.of(
//                new ProductDTO(brands.get(0).getId(), 10000),
//                new ProductDTO(brands.get(1).getId(), 20000),
//                new ProductDTO(brands.get(0).getId(), 30000))
//        );
//        this.em.close();
////        this.brandRepository.deleteById(brands.get(0).getId());
//        this.productRepositoryImpl.delete(products.get(0).getId());
//        this.productRepositoryImpl.findAll().forEach(System.out::println);
//        this.brandRepository.findAll().forEach(System.out::println);
    }

}
