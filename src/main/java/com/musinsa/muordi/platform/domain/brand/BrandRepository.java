package com.musinsa.muordi.platform.domain.brand;

import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

/**
 * 브랜드 DML을 정의한다.
 */
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    /**
     * 명칭으로 브랜드를 조회한다.
     * @param name 조회할 브랜드 명칭
     * @return 발견한 브랜드 리스트, 또는 빈 리스트
     */
    List<Brand> findByName(String name);

    /**
     * 브랜드 식별자를 조회하여 존재 시 쓰기락을 걸어 다른 스레드로 부터의 요청을 차단한다. 이 작업은 Transaction 상황에서만 사용할 수 있다.
     * 락은 락을 건 스레드에 의해 해지되어야 한다. // 만약 락이 해제되지 않는다면 일정시간 뒤 정리된다.
     * @param id 브랜드 식별자.
     * @return 수정 성공 시 수정한 브랜드를 감싼, 그 외에는 비어있는 Optional을 반환한다.
     * @throws PessimisticLockException 락 획득에 실패하였다.
     * @throws LockTimeoutException 락 획득 도중 타임아웃 발생.
     * @throws PersistenceException
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10")})
    default Optional<Brand> findByIdWithLock(int id) {
        return this.findById(id);
    }

    /**
     * 브랜드를 수정한다. 브랜드가 존재하는 경우에만 수정이 가능하며, 없는 경우 실패로 간주한다.
     * 이 작업은 읽기 -> 배타적 락 -> 쓰기 순서로 진행하므로 호출처에서 반드시 Transaction 락 걸기를 선행해야한다.
     * @param id 수정 대상 브랜드  식별자
     * @param brand 수정할 브랜드 내용.
     * @return 수정 성공 시 수정한 브랜드를 감싼, 그 외에는 비어있는 Optional을 반환한다.
     * @throws PessimisticLockException 락 획득에 실패하였다.
     * @throws LockTimeoutException 락 획득 도중 타임아웃 발생.
     * @throws PersistenceException
     */
    default Optional<Brand> updateById(int id, Brand brand) {
        Brand target = this.findByIdWithLock(id).orElse(null);
        if (target == null) {
            return Optional.empty();
        }
        target.setName(brand.getName());
        Brand updated = this.save(target);
        return Optional.of(updated);
    }
}
