package com.musinsa.muordi.common.exception;

import com.musinsa.muordi.common.util.ExceptionController;
import lombok.Getter;

/**
 * 데이터 무결성 결함 발생.
 */
@Getter
public class RepositoryIntegrityException extends RepositoryException implements ExceptionController.Attributer {
    private final Object key;

    public RepositoryIntegrityException(String repository, Object key) {
        super(repository, "%s 의 %s 을(를) 다른 곳에서 참조중입니다".formatted(repository, key));
        this.key = key;
    }

    @Override
    public Object getDetailAttribute() {
        return AttrTargetKeyReferenced.builder().target(repository).key(key).build();
    }
}
