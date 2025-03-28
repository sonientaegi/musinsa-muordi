package com.musinsa.muordi.platform.domain.category;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository repository;

    private Map<Integer, Category> targets = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        this.repository.saveAll(
            List.of(
                new Category(null, "CATEGORY 3", 5),
                new Category(null, "CATEGORY 1", 1),
                new Category(null, "CATEGORY 2", 3),
                new Category(null, "CATEGORY 3", 2),
                new Category(null, "CATEGORY 4", 4)
            )
        ).forEach(category -> this.targets.put(category.getId(), category));
    }

    @AfterEach
    void tearDown() {
    }

    // 전시순서 정렬 확인.
    @Test
    @Transactional
    void allWithSorted() {
        
        List<CategoryDTO> dtos = repository.all();
        assertNotNull(dtos);
        assertNotEquals(0, dtos.size());
        for(int i = 1; i < dtos.size(); i++) {
            assertTrue(dtos.get(i-1).getDisplaySequence() < dtos.get(i).getDisplaySequence());
        }
    }
    
    // 빈 카테고리 조회시 빈 배열 반환 확인.
    @Test
    @Transactional
    void allWithEmptied() {
        this.repository.deleteAll();
        List<CategoryDTO> targets = repository.all();
        assertNotNull(targets);
        assertEquals(targets.size(), 0);
    }

    // 식별자별 조회 결과 확인.
    @Test
    @Transactional
    void byId() {
        this.targets.forEach((id, target) -> {
            CategoryDTO source = this.repository.byId(id);
            assertTrue(source.getId().equals(target.getId()) && source.getName().equals(target.getName()) && source.getDisplaySequence() == target.getDisplay_sequence());
        });
    }

    // 존재하지않는 식별자 조회 결과 null 확인.
    @Test
    @Transactional
    void byIdNotExists() {
        assertNull(this.repository.byId(999));
    }

    // 이름별 조회 결과 및 정렬 확인.
    @Test
    @Transactional
    void byName() {
        // 이름별 조회 결과 확인.
        Map<String, List<Category>> targetsByName = new HashMap<>();
        targets.forEach((id, target) -> {
            targetsByName.computeIfAbsent(target.getName(), k -> new ArrayList<>()).add(target);
        });

        targetsByName.forEach((name, target) -> {
            List<CategoryDTO> sources = this.repository.byName(name);

            // 이름 확인.
            sources.forEach(source -> assertEquals(source.getName(), name));

            // 전시순서 정렬 확인.
            for(int i = 1; i < sources.size(); i++) {
                assertTrue(sources.get(i-1).getDisplaySequence() < sources.get(i).getDisplaySequence());
            }
        });
    }

    // 존재하지않는 이름 조회 결과 null 확인.
    @Test
    @Transactional
    void byNameNotExists() {
        List<CategoryDTO> targets = this.repository.byName("CATEGORY 999");
        assertNotNull(targets);
        assertEquals(0, targets.size());
    }
}