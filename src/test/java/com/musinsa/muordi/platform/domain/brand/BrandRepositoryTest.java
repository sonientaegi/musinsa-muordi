package com.musinsa.muordi.platform.domain.brand;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandRepositoryTest {
    @Autowired
    BrandRepository repository;

    private Map<Integer, BrandDTO> targets = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 테스트 케이스 생성.
        this.repository.saveAll(
            List.of(
                new BrandDTO(null, "BRAND 3"),
                new BrandDTO(null, "BRAND 4"),
                new BrandDTO(null, "BRAND 2"),
                new BrandDTO(null, "BRAND 1"),
                new BrandDTO(null, "BRAND 4")
            )
        ).forEach(brand -> this.targets.put(brand.getId(), brand));
    }

//    @AfterEach
//    void tearDown() {
//    }

    // 모든 브랜드 조회
    @Test
    @Transactional
    void allWithSorted() {
        List<BrandDTO> sources = repository.all();
        assertNotNull(sources);
        assertEquals(this.targets.size(), sources.size());
        sources.forEach(source -> {
            BrandDTO target = targets.get(source.getId());
            assertNotNull(target);
            assertEquals(target, source);
        });
    }
    
    // 빈 브랜드 조회시 빈 배열 반환 확인.
    @Test
    @Transactional
    void allWithEmptied() {
        this.repository.deleteAll();
        List<BrandDTO> targets = repository.all();
        assertNotNull(targets);
        assertEquals(0, targets.size());
    }

    // 식별자별 조회 결과 확인.
    @Test
    @Transactional
    void byId() {
        this.targets.forEach((id, target) -> {
            BrandDTO source = this.repository.byId(id);
            assertEquals(target, source);
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
        Map<String, List<BrandDTO>> targetsByName = new HashMap<>();
        targets.forEach((id, target) -> {
            targetsByName.computeIfAbsent(target.getName(), k -> new ArrayList<>()).add(target);
        });

        targetsByName.forEach((name, target) -> {
            List<BrandDTO> sources = this.repository.byName(name);

            // 이름 확인.
            sources.forEach(source -> assertEquals(name, source.getName()));
        });
    }

    // 존재하지않는 이름 조회 결과 null 확인.
    @Test
    @Transactional
    void byNameNotExists() {
        List<BrandDTO> sources = this.repository.byName("BRAND 999");
        assertNotNull(sources);
        assertEquals(0, sources.size());
    }

    // 단건 저장
    @Test
    @Transactional
    void save() {
        BrandDTO target = new BrandDTO(null, "TEMPORARY BRAND");
        BrandDTO source = this.repository.save(target);
        assertNotNull(source);
        assertNotNull(source.getId());
        assertEquals(target.getName(), source.getName());
    }

    // 단건 갱신
    @Test
    @Transactional
    void update() {
        BrandDTO target = this.targets.get(this.targets.keySet().iterator().next());
        target.setName("UPDATED BRAND");
        assertEquals(target, this.repository.save(target));
        assertEquals(target, this.repository.byId(target.getId()));
    }

    // 다건 저장
    @Test
    @Transactional
    void saveAll() {
        BrandDTO source = new BrandDTO(null, "TEMPORARY BRAND");
        BrandDTO target = this.repository.save(source);
        assertNotNull(target);
        assertNotNull(target.getId());
        assertEquals(target.getName(), source.getName());
    }

    // 존재하지 않는 키 저장
    // 예외 발생 : ObjectOptimisticLockingFailureException
    @Test
    @Transactional
    void saveNotExists() {
        BrandDTO source = this.targets.get(this.targets.keySet().iterator().next());
        source.setId(999);
        assertThrows(ObjectOptimisticLockingFailureException.class, ()->this.repository.save(source));
    }

    // 다건 저장 & 갱신
    @Test
    @Transactional
    void saveAndUpdate() {
        List<BrandDTO> modifiedTargets = this.targets.values().stream().map(target -> (BrandDTO)target.clone()).toList();
        modifiedTargets.get(0).setId(null);                 // 생성
        modifiedTargets.get(1).setName("UPDATED BRAND 1");  // 수정
        // modifiedTargets.get(2).setId(null);              // --
        modifiedTargets.get(3).setId(null);                 // 생성
        modifiedTargets.get(4).setName("UPDATED BRAND 2");  // 수정

//        Random rand = new Random();
//        modifiedTargets.forEach(target -> {
//            switch(rand.nextInt(3)) {
//                case 0:
//                    target.setId(null);
//                    break;
//                case 1:
//                    target.setName("UPDATED BRAND" + rand.nextInt());
//                    break;
//                case 2:
//                    // Do nothing!
//                    break;
//            }
//        });

        List<BrandDTO> sources = this.repository.saveAll(modifiedTargets);
        for (int i=0; i < sources.size(); i++) {
            BrandDTO target = modifiedTargets.get(i);
            BrandDTO source = sources.get(i);
            if (target.getId() == null) {
                assertEquals(target.getName(), source.getName());
            } else {
                assertEquals(target, source);
            }
        }
    }

    // 단건 삭제
    @Test
    @Transactional
    void delete() {
        BrandDTO source = this.targets.get(this.targets.keySet().iterator().next());
        this.repository.deleteById(source.getId());
        assertNull(this.repository.byId(source.getId()));
    }

    // 존재하지 않는 레코드 삭제
    @Test
    @Transactional
    void deleteNotExists() {
        this.repository.deleteById(999);
        assertNull(this.repository.byId(999));
    }
}