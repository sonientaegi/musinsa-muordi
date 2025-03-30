package com.musinsa.muordi.platform.domain.category;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test-category")
@SpringBootTest
public class CategoryRepositoryTest {
    /**
     * DTO 테스트를 지원하기위해 제공하는 샘플 데이터 입니다.
     * @return
     */
    public static Category sample() {
        return new Category(1, "SAMPLE", 1);
    }

    @Autowired
    CategoryRepository repository;

    // 테스트케이스
    private List<Category> testCases = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        this.testCases = this.repository.saveAll(
            List.of(
                new Category(null, "CATEGORY 3", 5),
                new Category(null, "CATEGORY 1", 1),
                new Category(null, "CATEGORY 2", 3),
                new Category(null, "CATEGORY 3", 2),
                new Category(null, "CATEGORY 4", 4)
            )
        );
    }

    @AfterEach
    void tearDown() {
    }

    // 전체 카테고리 조회 시 전시순서 정렬 확인.
    @Test
    @Transactional
    void all() {
        List<Category> categories = repository.findAll();
        assertNotNull(categories);
        assertEquals(this.testCases.size(), categories.size());
        IntStream.range(1, categories.size()).forEach(i -> {
            assertTrue(categories.get(i-1).getDisplaySequence() < categories.get(i).getDisplaySequence());
        });
    }

    // 빈 카테고리 조회시 빈 리스트 반환 확인.
    @Test
    @Transactional
    void allWithEmptied() {
        this.repository.deleteAll();
        List<Category> actual = this.repository.findAll();
        assertNotNull(actual);
        assertEquals(actual.size(), 0);
    }

    // 카테고리 식별자별 조회 결과 확인.
    @Test
    @Transactional
    void byId() {
        this.testCases.forEach(expected -> {
            assertEquals(expected, this.repository.findById(expected.getId()).orElse(null));
        });
    };

    // 존재하지않는 식별자 조회 결과 null 확인.
    @Test
    @Transactional
    void byIdNotExists() {
        assertNull(this.repository.findById(Integer.MAX_VALUE).orElse(null));
    }

    // 이름 조회 결과와 정렬 확인.
    @Test
    @Transactional
    void byName() {
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
            assertEquals(expected.get(name), this.repository.findByNameOrderByDisplaySequenceAsc(name));
        });
    }

    // 존재하지않는 카테고리 조회 결과 null 또는 빈 리스트 확인.
    @Test
    @Transactional
    void byNameNotExists() {
        assertThrows(NoSuchElementException.class, () -> this.repository.findById(Integer.MAX_VALUE).get());
        assertEquals(0, this.repository.findByName("CATEGORY" + Integer.MAX_VALUE).size());
    }


    // 전시순서 정합성 제약조건 확인.
    // 이미 존재하는 display_sequence 값을 입력하는 경우 unique key 조건을 위배하여 무결성오류예외를 반환한다.
    @Test
    @Transactional
    void createCategoryExistedDisplaySequence() {
        assertThrows(DataIntegrityViolationException.class, () -> this.repository.save(new Category(null, "CATEGORY" + Integer.MAX_VALUE, 1)));
    }
}