package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/*
BRAND 의 JpaRepository 테스트
 */
@ActiveProfiles("test-empty")
@SpringBootTest
public class BrandRepositoryJpaWrapperTest {
    @Autowired
    private BrandRepositoryJpaWrapper repository;

    private Map<Integer, Brand> testCases = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        List.of(
            new Brand(null, "BRAND 3", null),
            new Brand(null, "BRAND 4", null),
            new Brand(null, "BRAND 2", null),
            new Brand(null, "BRAND 1", null),
            new Brand(null, "BRAND 4", null)
        ).forEach(brand -> {
            brand = this.repository.create(brand);
            this.testCases.put(brand.getId(), brand);
        });
    }

    @Test
    @DisplayName("모든 브랜드 조회")
    @Transactional
    void testAll() {
        List<Brand> actuals = repository.findAll();
        assertNotNull(actuals);
        assertEquals(this.testCases.size(), actuals.size());
        actuals.forEach(actual -> {
            // ID 검증
            Brand expected = this.testCases.get(actual.getId());
            assertEquals(expected, actual);
        });
    }

    @Test
    @DisplayName("브랜드 ID 조회")
    @Transactional
    void testFindById() {
        this.testCases.values().forEach(testCase -> {
            assertEquals(testCase, this.repository.findById(testCase.getId()).orElse(null));
        });
    }

    @Test
    @DisplayName("브랜드 이름 조회")
    @Transactional
    void testFindByName() {
        this.testCases.values().stream().map(Brand::getName).forEach(name -> {
            List<Brand> actuals = this.repository.findByName(name);
            assertNotNull(actuals);
            actuals.forEach(actual -> {
                Brand expected = this.testCases.get(actual.getId());
                assertEquals(expected, actual);
            });
        });
    }

    @Test
    @DisplayName("브랜드 ID 조회 - 존재하지 않는 ID")
    @Transactional
    void testFindByIdNotExists() {
        assertNull(this.repository.findById(Integer.MIN_VALUE).orElse(null));
    }


    @Test
    @DisplayName("브랜드 이름 조회 - 존재하지 않는 이름")
    @Transactional
    void testFindByNameNotExists() {
        assertEquals(0, this.repository.findByName("BRAND" + Integer.MAX_VALUE).size());
    }

    @Test
    @DisplayName("브랜드 저장")
    @Transactional
    void testSave() {
        Brand target = new Brand(null, "BRAND" + Integer.MAX_VALUE,null);
        Brand actual = this.repository.create(target);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertTrue(target.getName().equals(actual.getName()));
    }

    @Test
    @DisplayName("브랜드 수정")
    @Transactional
    void testUpdate() {
        Brand testCase = this.testCases.values().iterator().next();
        Brand expected = testCase.clone();
        expected.setName("BRAND" + Integer.MAX_VALUE);
        Brand actual = this.repository.create(expected);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("브랜드 수정 - 존재하지 않는 ID")
    @Transactional
    void testUndateNotExists() {
        // 예외 발생해야함.
        Brand expected = new Brand(null, "MAL BRAND", null);
        assertThrows(RepositoryEntityNotExistException.class, ()->this.repository.update(Integer.MAX_VALUE, expected));
    }

    @Test
    @DisplayName("브랜드 삭제")
    @Transactional
    void delete() {
        Brand tc = this.testCases.values().iterator().next();
        this.repository.delete(tc.getId());
        assertNull(this.repository.findById(tc.getId()).orElse(null));
    }

    @Test
    @DisplayName("브랜드 삭제 - 존재하지 않는 ID")
    @Transactional
    void deleteNotExists() {
        assertThrows(RepositoryEntityNotExistException.class, ()->this.repository.delete(Integer.MAX_VALUE));
    }
}