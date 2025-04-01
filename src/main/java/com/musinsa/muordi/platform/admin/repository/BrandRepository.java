package com.musinsa.muordi.platform.admin.repository;

import com.musinsa.muordi.common.exception.RepositoryEntityIntegrityViolation;
import com.musinsa.muordi.common.exception.RepositoryEntityNotExistException;
import lombok.NonNull;
import org.hibernate.PessimisticLockException;

import java.util.List;
import java.util.Optional;

/**
 * 브랜드 repository 의 DML을 명세한 인터페이스이다.
 */
public interface BrandRepository {
    /**
     * 모든 브랜드를 반환한다.
     * @return 브랜드 entity 리스트를 반환한다.
     */
    List<Brand> findAll();

    /**
     * ID로 브랜드를 조회한다. ID는 브랜드의 유일한 식별자이며 두개이상의 같은값은 존재하지 않는다.
     * @param id 브랜드 ID
     * @return Optional로 감싼 브랜드 entity, 미발견 시 빈 Optional을 반환한다. null 일 수 없다.
     */
    Optional<Brand> findById(int id);

    /**
     * 이름으로 브랜드를 조회한다. 같은 이름의 브랜드가 2개 이상 존재할 수 있다.
     * @param name 조회할 브랜드 이릌.
     * @return 발견한 브랜드 entity 리스트를 반환한다. 미발견 시 빈 리스트를 반환한다.
     */
    List<Brand> findByName(String name);

    /**
     * 새로운 브랜드를 생성한다. 실패시 Repository 구현체에서 예외를 반환한다.
     * @param brand 생성할 브랜드.
     * @return 생성한 브랜드 entity. 브랜드 ID를 포함한다. null 일 수 없다.
     */
    Brand create(@NonNull Brand brand);

    /**
     * 기존의 브랜드를 수정한다. 이 동작은 원자적 실행을 지원한다. 만약 ID로 브랜드를 발견하지 못한다면 예외를 반환한다.
     * @param id 수정할 브랜드 ID.
     * @param brand 수정할 브랜드 내용.
     * @return 수정한 브랜드 entity. null 일 수 없다.
     * @throws RepositoryEntityNotExistException 수정하려는 브랜드가 존재하지 않는다.
     * @throws PessimisticLockException 락 획득에 실패했다.
     */
    Brand update(int id, @NonNull Brand brand);

    /**
     * 기존의 브랜드를 삭제하고, 기존 브랜드의 entity를 반환한다. 실패시 예외를 반환한다.
     * @param id 삭제할 브랜드 ID.
     * @return 삭제 한 브랜드 entity. null 일 수 없다.
     * @throws RepositoryEntityNotExistException 삭제하려는 브랜드가 존재하지 않는다.
     * @throws RepositoryEntityIntegrityViolation 정합성 오류로 인해 브랜드를 삭제할 수 없다.
     */
    Brand delete(int id);
}
