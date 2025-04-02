package com.musinsa.muordi.contents.display.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/*
CATEGORY 의 JpaRepository 테스트
 */
@ActiveProfiles("test-empty")
@SpringBootTest
public class CategoryRepositoryJpaWrapperTest {
    @Autowired
    CategoryRepositoryJpaWrapper repository;

    @Autowired
    CategoryRepositoryJpa repositoryJpa;

    // 테스트케이스
    private List<Category> testCases = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        this.testCases = this.repositoryJpa.saveAll(
            List.of(
                new Category(null, "CATEGORY 3", 5),
                new Category(null, "CATEGORY 1", 1),
                new Category(null, "CATEGORY 2", 3),
                new Category(null, "CATEGORY 3", 2),
                new Category(null, "CATEGORY 4", 4)
            )
        );
    }

    @Test
    @DisplayName("전체 카테고리 조회 시 전시순서 정렬 확인.")
    @Transactional
    void testAll() {
        List<Category> categories = repository.findAll();
        assertNotNull(categories);
        assertEquals(this.testCases.size(), categories.size());
        IntStream.range(1, categories.size()).forEach(i -> {
            assertTrue(categories.get(i-1).getDisplaySequence() < categories.get(i).getDisplaySequence());
        });
    }


    @Test
    @DisplayName("빈 카테고리 조회")
    @Transactional
    void testAllWithEmptied() {
        this.repositoryJpa.deleteAll();

        List<Category> actual = this.repository.findAll();
        assertNotNull(actual);
        assertEquals(actual.size(), 0);
    }

    @Test
    @DisplayName("카테고리 ID 조회")
    @Transactional
    void testFindById() {
        this.testCases.forEach(expected -> {
            assertEquals(expected, this.repository.findById(expected.getId()).orElse(null));
        });
    }

    @Test
    @DisplayName("카테고리 ID 조회 - 없는 ID")
    @Transactional
    void testFindByIdNotExists() {
        assertNull(this.repository.findById(Integer.MAX_VALUE).orElse(null));
    }

    @Test
    @DisplayName("카테고리 이름 조회 & 정렬 확인")
    @Transactional
    void testFindByName() {
        // 테스트케이스를 이름순으로 분류 후 결과를 전시 오름차순 정렬.
        Map<String, List<Category>> expected = new HashMap<>();
        this.testCases.forEach(category -> {
            expected.computeIfAbsent(category.getName(), name -> new ArrayList<>()).add(category);
        });
        expected.values().stream().forEach(
                categories -> categories.sort(Comparator.comparingInt(Category::getDisplaySequence))
        );

        // 이름별 조회 결과와 기댓값을 비교.
        this.testCases.stream().map(Category::getName).forEach(name -> {
            assertEquals(expected.get(name), this.repository.findByName(name));
        });
    }

    @Test
    @DisplayName("카테고리 이름 조회 - 없는 이름")
    @Transactional
    void testFindByNameNotExists() {
        assertThrows(NoSuchElementException.class, () -> this.repository.findById(Integer.MAX_VALUE).get());
        assertEquals(0, this.repository.findByName("CATEGORY" + Integer.MAX_VALUE).size());
    }
}