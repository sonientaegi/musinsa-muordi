package com.musinsa.muordi.platform.admin.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/*
JpaRepository의 save 동작 검증용. Create and Update 확인 용도로 테스트 함.
*/
@ActiveProfiles("test-empty")
@SpringBootTest
public class BrandRepositoryJpaTest {
    @Autowired
    private BrandRepositoryJpa repository;

    private Map<Integer, Brand> testCases = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        this.repository.saveAll(
                List.of(
                        new Brand(null, "BRAND 3", null),
                        new Brand(null, "BRAND 4", null),
                        new Brand(null, "BRAND 2", null),
                        new Brand(null, "BRAND 1", null),
                        new Brand(null, "BRAND 4", null)
                )
        ).forEach(brand -> {
            this.testCases.put(brand.getId(), brand);
        });
    }

    @Test
    @DisplayName("다건 저장 & 수정")
    @Transactional
    void testSaveAndUpdate() {
        Function<Brand,Brand> copyForTest = (src) -> {
            Brand dst = (Brand)src.clone();
            dst.setName("MODIFIED " + src.getName());
            return dst;
        };

        // 생성 & 수정 케이스 생성
        Iterator<Brand> iterator = this.testCases.values().iterator();
        List<Brand> expecteds = List.of(new Brand[]{
                new Brand(null, "NEW BRAND 1", null), // 생성
                copyForTest.apply(iterator.next()), // 수정
                new Brand(null, "NEW BRAND 2", null), // 생성
                copyForTest.apply(iterator.next()), // 수정
                new Brand(null, "NEW BRAND 3", null), // 생성
                copyForTest.apply(iterator.next()), // 수정
        });

        // 검증. 신규 생성의 경우 식별자를 새로 채번한것을 제외하고, 나머지 모든 값은 같아야 한다.
        List<Brand> actuals = this.repository.saveAll(expecteds);
        assertNotNull(actuals);
        assertEquals(expecteds.size(), actuals.size());
        for (int i=0; i < actuals.size(); i++) {
            Brand expected = expecteds.get(i);
            Brand actual = actuals.get(i);

            // 식별자 확인.
            if (expected.getId() != null) {
                // 수정 시
                assertEquals(expected.getId(), actual.getId());
            } else {
                // 생성 시
                assertNotNull(actual.getId());
            }

            // 값 확인
            assertTrue(expected.getName().equals(actual.getName()));
        }
    }
}