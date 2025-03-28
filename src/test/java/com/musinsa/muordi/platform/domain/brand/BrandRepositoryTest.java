package com.musinsa.muordi.platform.domain.brand;

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
class BrandRepositoryTest {
    @Autowired
    BrandRepository repository;

    private Map<Integer, Brand> targets = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        this.repository.saveAll(
            List.of(
                new Brand(null, "BRAND 3"),
                new Brand(null, "BRAND 4"),
                new Brand(null, "BRAND 2"),
                new Brand(null, "BRAND 1"),
                new Brand(null, "BRAND 4")
            )
        ).forEach(brand -> this.targets.put(brand.getId(), brand));
    }

    @AfterEach
    void tearDown() {
    }

    // 모든 브랜드 조회
    @Test
    @Transactional
    void allWithSorted() {
        List<BrandDTO> sources = repository.all();
        assertNotNull(sources);
        assertNotEquals(0, sources.size());
        sources.forEach(source -> {
            Brand target = targets.get(source.getId());
            assertNotNull(target);
            assertTrue(source.getId().equals(target.getId()) && source.getName().equals(target.getName()));
        });
    }
    
    // 빈 브랜드 조회시 빈 배열 반환 확인.
    @Test
    @Transactional
    void allWithEmptied() {
        this.repository.deleteAll();
        List<BrandDTO> targets = repository.all();
        assertNotNull(targets);
        assertEquals(targets.size(), 0);
    }

    // 식별자별 조회 결과 확인.
    @Test
    @Transactional
    void byId() {
        this.targets.forEach((id, target) -> {
            BrandDTO source = this.repository.byId(id);
            assertTrue(source.getId().equals(target.getId()) && source.getName().equals(target.getName()));
        });
    }

    // 존재하지않는 식별자 조회 결과 null 확인.
    @Test
    @Transactional
    void byIdNotExists() {
        assertNull(this.repository.byId(999));
    }

    // 이름별 조회 결과 확인.
    @Test
    @Transactional
    void byName() {
        // 이름별 조회 결과 확인.
        Map<String, List<Brand>> targetsByName = new HashMap<>();
        targets.forEach((id, target) -> {
            targetsByName.computeIfAbsent(target.getName(), k -> new ArrayList<>()).add(target);
        });

        targetsByName.forEach((name, target) -> {
            List<BrandDTO> sources = this.repository.byName(name);

            // 이름 확인.
            sources.forEach(source -> assertEquals(source.getName(), name));
        });
    }

    // 존재하지않는 이름 조회 결과 null 확인.
    @Test
    @Transactional
    void byNameNotExists() {
        List<BrandDTO> targets = this.repository.byName("BRAND 999");
        assertNotNull(targets);
        assertEquals(0, targets.size());
    }
}