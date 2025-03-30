package com.musinsa.muordi.common.jpa;

/**
 * {@link AtomicUpdateById}안에서 수정 시 entity의 복사에 대한 명세를 선언한다.
 * @param <T> entity 타입.
 */
public interface EntityUpdate<T> {
    /**
     * entity의 복사를 구현한다. 변경 가능한 필드에 대한 복사를 구현해야 하며 PK나 읽기 전용 필드는 복사해서는 안된다.
     * @param src 원본 데이터
     */
    void updateFrom(T src);
}
