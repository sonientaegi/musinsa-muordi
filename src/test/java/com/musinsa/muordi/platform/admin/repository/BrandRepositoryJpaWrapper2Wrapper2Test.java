package com.musinsa.muordi.platform.admin.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test-admin-brand")
@SpringBootTest
public class BrandRepositoryJpaWrapper2Wrapper2Test {
    /**
     * DTO 테스트를 지원하기위해 제공하는 샘플 데이터 입니다.
     * @return
     */
    public static Brand sample() {
        return new Brand(1, "BRAND", null);
    }

    @Autowired
    private BrandRepositoryJpa2 repository;

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
        ).forEach(brand -> this.testCases.put(brand.getId(), brand));
    }

//    @AfterEach
//    void tearDown() {
//    }

    // 모든 브랜드 조회.
    @Test
    @Transactional
    void all() {
        List<Brand> actuals = repository.findAll();
        assertNotNull(actuals);
        assertEquals(this.testCases.size(), actuals.size());
        actuals.forEach(actual -> {
            Brand expected = this.testCases.get(actual.getId());
            assertEquals(expected, actual);
        });
    }

    // 식별자 조회.
    @Test
    @Transactional
    void  findById() {
        this.testCases.values().forEach(testCase -> {
            assertEquals(testCase, this.repository.findById(testCase.getId()).orElse(null));
        });
    }

    // 명칭 조회.
    @Test
    @Transactional
    void findByName() {
        this.testCases.values().stream().map(Brand::getName).forEach(name -> {
            List<Brand> actuals = this.repository.findByName(name);
            assertNotNull(actuals);
            actuals.forEach(actual -> {
                Brand expected = this.testCases.get(actual.getId());
                assertEquals(expected, actual);
            });
        });
    }

    // 존재하지 않는 브랜드 식별자 조회.
    //
    @Test
    @Transactional
    void findByIdNotExists() {
        assertThrows(NoSuchElementException.class, () -> {this.repository.findById(Integer.MIN_VALUE).get();});
    }

    // 존재하지 않는 브랜드 명칭 조회ㅣ.
    @Test
    @Transactional
    void findByNameNotExists() {
        assertEquals(0, this.repository.findByName("BRAND" + Integer.MAX_VALUE).size());
    }

    // 단건 저장
    @Test
    @Transactional
    void save() {
        Brand target = new Brand(null, "BRAND" + Integer.MAX_VALUE,null);
        Brand actual = this.repository.save(target);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertTrue(target.getName().equals(actual.getName()));
    }

    // 단건 갱신
    @Test
    @Transactional
    void update() {
        Brand testCase = this.testCases.values().iterator().next();
        Brand expected = (Brand) testCase.clone();
        expected.setName("BRAND" + Integer.MAX_VALUE);
        Brand actual = this.repository.save(expected);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    // 다건 저장
    @Test
    @Transactional
    void saveAll() {
        List<Brand> expecteds = this.testCases.values().stream().map(tc -> {
            Brand expect = new Brand(null, "NEW " + tc.getName(), null);
            return expect;
        }).toList();

        List<Brand> actuals = this.repository.saveAll(expecteds);
        assertNotNull(actuals);
        assertEquals(expecteds.size(), actuals.size());
        for(int i = 0; i < expecteds.size(); i++) {
            Brand actual = actuals.get(i);
            Brand expected = expecteds.get(i);
            assertTrue(actual.getName().equals(expected.getName()));
        }
    }

    // 존재하지 않는 키 저장
    // 예외 발생 : ObjectOptimisticLockingFailureException
    @Test
    @Transactional // <- 일부러 rollback을 일으킬거임.
    void saveNotExists() {
        Brand expected = new Brand(Integer.MAX_VALUE, "MAL BRAND", null);
        assertThrows(ObjectOptimisticLockingFailureException.class, ()->this.repository.save(expected));
    }

    // 다건 저장 & 갱신
    @Test
    @Transactional
    void saveAndUpdate() {
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

    // 단건 삭제
    @Test
    @Transactional
    void delete() {
        Brand tc = this.testCases.values().iterator().next();
        this.repository.deleteById(tc.getId());
        assertNull(this.repository.findById(tc.getId()).orElse(null));
    }

    // 존재하지 않는 레코드 삭제
    @Test
    @Transactional
    void deleteNotExists() {
        int fakeId = Integer.MAX_VALUE;
        assertNull(this.repository.findById(fakeId).orElse(null));
        assertDoesNotThrow(() -> this.repository.deleteById(fakeId));
    }
}