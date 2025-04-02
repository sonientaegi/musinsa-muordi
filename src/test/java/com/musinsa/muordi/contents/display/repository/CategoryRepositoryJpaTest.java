package com.musinsa.muordi.contents.display.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/*
CATEGORY 의 JpaRepository 테스트
 */
@ActiveProfiles("test-empty")
@SpringBootTest
public class CategoryRepositoryJpaTest {
    /**
     * DTO 테스트를 지원하기위해 제공하는 샘플 데이터 입니다.
     * @return
     */
    public static Category sample() {
        return new Category(1, "SAMPLE", 1);
    }

    @Autowired
    CategoryRepositoryJpa repository;

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

    @Test
    @DisplayName("전시순서 정합성 제약조건 확인")
    // 이미 존재하는 display_sequence 값을 입력하는 경우 unique key 조건을 위배하여 무결성오류예외를 반환한다.
    @Transactional
    void testCreateCategoryExistedDisplaySequence() {
        assertThrows(DataIntegrityViolationException.class, () -> this.repository.save(new Category(null, "CATEGORY" + Integer.MAX_VALUE, 1)));
    }
}