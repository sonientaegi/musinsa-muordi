package com.musinsa.muordi.contents.display.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test-display-showcase")
@SpringBootTest
class ShowcaseRepositoryJpaWrapperTest {
    @Autowired
    private ShowcaseRepositoryJpaWrapper repository;

    /**
     * 무작위로 한개의 쇼케이스 레코드를 선택한다.
     *
     */
    private Showcase randShowcase() {
        List<Showcase> showcases = this.repository.findAll();
        return showcases.get(new Random().nextInt(showcases.size()));
    }

    // 쇼케이스 전체 조회.
    @Test
    @DisplayName("쇼케이스 전체 조회")
    @Transactional
    void testFindAll() {
        List<Showcase> showcases = this.repository.findAll();
        assertNotNull(showcases);
        assertTrue(showcases.size() > 0);
    }

    // 쇼케이스 상품 식별자 조회
    @Test
    @DisplayName("쇼케이스 상품 식별자 조회")
    @Transactional
    void testFindByProductId() {
        Showcase expected = this.randShowcase();
        Optional<Showcase> actual = this.repository.findByProductId(expected.getProduct().getId());
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    // 브랜드 식별자로 쇼케이스 조회
    @Test
    @DisplayName("브랜드 식별자로 쇼케이스 조회")
    @Transactional
    void testFindByBrandId() {
        int expected = this.randShowcase().getProduct().getBrand().getId();
        List<Showcase> actuals = this.repository.findByBrandId(expected);
//        List<Showcase> actuals = this.repository.findAllByBrand_Id(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> {
            assertEquals(expected, actual.getProduct().getBrand().getId());
        });
    }

    // 브랜드 이름으로 쇼케이스 조회
    @Test
    @DisplayName("브랜드 이름으로 쇼케이스 조회")
    @Transactional
    void testFindByBrandName() {
        String expected = this.randShowcase().getProduct().getBrand().getName();
        List<Showcase> actuals = this.repository.findByBrandName(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> {
            assertEquals(expected, actual.getProduct().getBrand().getName());
        });
    }

    // 카테고리 식별자로 쇼케이스 조회
    @Test
    @DisplayName("카테고리 식별지로 쇼케이스 조회")
    @Transactional
    void testFindByCategoryId() {
        int expected = this.randShowcase().getCategory().getId();
        List<Showcase> actuals = this.repository.findByCategoryId(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> {
            assertEquals(expected, actual.getCategory().getId());
        });
    }

    // 카테고리 이름으로 쇼케이스 조회
    @Test
    @DisplayName("카테고리 이름으로 쇼케이스 조회")
    @Transactional
    void testFindByCategoryName() {
        String expected = this.randShowcase().getCategory().getName();
        List<Showcase> actuals = this.repository.findByCategoryName(expected);
        assertNotNull(actuals);
        assertTrue(actuals.size() > 0);
        actuals.forEach(actual -> {
            assertEquals(expected, actual.getCategory().getName());
        });
    }

    // 쇼케이스 생성
    @Test
    @DisplayName("쇼케이스 생성")
    @Transactional
    void testCreate() {
        // 쇼케이스 생성을 위해 기존의 레코드를 하나 지운 뒤 다시 생성한다.
        Showcase target = this.randShowcase();
        int originId = target.getId();
        this.repository.delete(originId);

        // 동일 트랜잭션에서 삭제 후 동일 UK를 추가하면 JPA가 정합성 오류를 반환하므로 읽기를 한번 수행한다.
        this.repository.findAll();

        Showcase expected = new Showcase();
        expected.setProduct(target.getProduct());
        expected.setCategory(target.getCategory());
        Showcase actual = this.repository.create(expected);
        this.repository.findAll();

        assertNotNull(actual);
        assertNotEquals(originId, actual.getId());
        assertEquals(expected.getProduct(), actual.getProduct());
        assertEquals(expected.getCategory(), actual.getCategory());
    }

    // 쇼케이스 수정
    @Test
    @DisplayName("쇼케이스 수정 - 카테고리 변경")
    @Transactional
    void testUpdateCategory() {
        // 쇼케이스의 카테고리 변경을 수행하기 위한 테스트 데이터 준비.
        Showcase target = this.randShowcase();
        Category expectedCategory = this.randShowcase().getCategory();
        while (expectedCategory.getId() == target.getCategory().getId()) {
            expectedCategory = this.randShowcase().getCategory();
        }
        target.setCategory(expectedCategory);
        Showcase actual = this.repository.update(target);
        assertNotNull(actual);
        assertEquals(expectedCategory, actual.getCategory());
    }

    // 쇼케이스 중복 생성 실패. 하나의 상품을 여러 카테고리에 등록할 수 없다.
    @Test
    @DisplayName("쇼케이스 중복 생성 실패 - 동일한 상품")
    @Transactional
    void testCreateDuplicatedProduct() {
        Showcase target = this.randShowcase();
        Category targetCategory = this.randShowcase().getCategory();
        while (targetCategory.getId() == target.getCategory().getId()) {
            targetCategory = this.randShowcase().getCategory();
        }

        // 상품 중복 등록을 위해 새로운 쇼케이스를 생성한다.
        Showcase expected = this.randShowcase();
        expected.setCategory(targetCategory);
        expected.setProduct(target.getProduct());
        Showcase actual = this.repository.create(expected);

        // JPA에 의해 읽기 수행 시 정합성 오류 발생.
        // 실서비스에서는 Transaction 종료 시 commit 중 예외 발생.
        assertThrows(DataIntegrityViolationException.class, () -> repository.findAll());
    }

    @Test
    @DisplayName("쇼케이스 삭제 - 상품 식졀자")
    @Transactional
    void testDelete() {
        Showcase expected = this.randShowcase();
        this.repository.delete(expected.getId());
        Optional<Showcase> actual = this.repository.findByProductId(expected.getProduct().getId());
        assertTrue(actual.isEmpty());
    }
}
