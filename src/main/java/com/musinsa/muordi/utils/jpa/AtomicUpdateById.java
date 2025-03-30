package com.musinsa.muordi.utils.jpa;

import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceException;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * JpaRepository에 read & update 기능을 추가한다. JPA의 save는 createOrUpdate 동작을 수행하므로
 * Key 값의 유무와 상관없이 항상 저장에 성공한다. 만약 생성과 수정을 별도로 구분하려면 JPQL 이나 NativeSQL을 사용하여
 * Repository 마다 일일이 Query를 생성해주어야한다. 이런 반복작업을 막고, Jpa가 제공하는 기능을 그대로 유지 & 확용하기 위해
 * 별도의 제네릭 인터페이스를 구현한다.<br><br>
 * 레코드를 "수정" 하려면 먼저 레코드의 존재 여부를 확인하고 그 다음 저장(= 수정)을 수행해야한다. 이 과정은 원자적이어야 하며
 * 그렇지 아니한 경우 수정 대신 생성 작업이 이루어질 수 있다. PK 관리 정책에 따라 이 작업은 성공할 수도, 실패할 수도 있다. 다음의
 * 예를 들어보자.<br><br>
 * PK를 임의로 지정할 수 있는 데이터베이스가 있다고 가정할때 사용자A가 레코드1을 수정하려하였다. 그 사이 사용자B가 레코드1이 잘못된
 * 데이터임을 확인하고 삭제조치하였다. 만약 삭제가 이루어지고 다시 수정이 이루어진다면 JPA의 save는 이걸 생성으로 처리한다.
 * 그리고 레코드1은 지워진 적 없는것처럼 다시 존재하게 된다. 만약 읽기와 수정이 원자적으로 이루어진다면 삭제가 먼저 진행되어 생성이
 * 불가하거나, 수정 완료 후 삭제를 진행햐여 데이터의 정합성을 유지할 수 있다.<br><br>
 * AtomicUpdateById 인터페이스는 원자적 수정을 위한 {@link AtomicUpdateById#updateById}를 제공한다. 이 동작은 다음과 같다.
 * <ol>
 *     <li>수정하고자 하는 레코드를 찾는다.</li>
 *     <li>레코드가 존재시 배타적 락을 건다.</li>
 *     <li>레코드를 수정한다.</li>
 * </ol>
 * {@link AtomicUpdateById#updateById} 호출처는 반드시 Transaction을 수행해야한다. Commit 또는 Rollback이 이루어질때
 * 락은 해제되며, 그렇지 않으면 DB에 의해 정리되기 전 까지 해당 레코드는 계속하여 배타적 잠금상태를 유지한다.
 * @param <T> entity 타입. 반드시  {@link EntityUpdate}를 구현해야한다.
 * @param <K> key 타입
 */
@NoRepositoryBean
public interface AtomicUpdateById<T extends EntityUpdate, K> extends JpaRepository<T, K> {
    // TODO 락 테스트, 락 힌트 등 구현.
    /**
     * 식별자를 조회하여 존재 시 쓰기락을 걸어 다른 스레드로 부터의 요청을 차단한다. 이 작업은 Transaction 상황에서만 사용할 수 있다.
     * 락은 락을 건 스레드에 의해 해지되어야 한다. // 만약 락이 해제되지 않는다면 일정시간 뒤 정리된다.
     * @param  id 식별자.
     * @return 수정 성공 시 수정한 레코드를 감싼, 그 외에는 비어있는 Optional을 반환한다.
     * @throws PessimisticLockException 락 획득에 실패하였다.
     * @throws LockTimeoutException 락 획득 도중 타임아웃 발생.
     * @throws PersistenceException
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10")})
    default Optional<T> findByIdWithLock(K id) {
        return this.findById(id);
    }

    /**
     * 레코드를 수정한다. 수정하고자 하는 레코드가가 존재하는 경우에만 수정이 가능하며, 없는 경우 실패로 간주한다.
     * 이 작업은 읽기 -> 배타적 락 -> 쓰기 순서로 진행하므로 호출처에서 반드시 Transaction 락 걸기를 선행해야한다.
     * @param id 수정 대상 레코드 식별자
     * @param source 수정할 레코드 내용.
     * @return 수정 성공 시 수정한 레코드를 감싼, 그 외에는 비어있는 Optional을 반환한다.
     * @throws PessimisticLockException 락 획득에 실패하였다.
     * @throws LockTimeoutException 락 획득 도중 타임아웃 발생.
     * @throws PersistenceException
     */
    default Optional<T> updateById(K id, T source) {
        T target = this.findByIdWithLock(id).orElse(null);
        if (target == null) {
            return Optional.empty();
        }
        target.updateFrom(source);
        T updated = this.save(target);
        return Optional.of(updated);
    }
}
