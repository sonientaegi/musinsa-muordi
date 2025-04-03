package com.musinsa.muordi.common.exception;

import com.musinsa.muordi.common.util.ExceptionController;
import lombok.Getter;

/**
 * CUD 하려는 데이터가 존재하지 않는다.
 */
@Getter
final public class RepositoryNotExistException extends RepositoryException implements ExceptionController.Attributer {
    private final Object key;
    private final Object referenced;

    public RepositoryNotExistException(String repository, Object key, Object referenced) {
        super(repository, "%s가 참조하는 %s의 키 %s가 존재하지 않습니다".formatted(referenced, repository, key.toString()));
        this.key = key;
        this.referenced = referenced;
    }

    public RepositoryNotExistException(String repository, Object key) {
        super(repository, "%s의 키 %s가 존재하지 않습니다".formatted(repository, key.toString()));
        this.key = key;
        this.referenced = null;
    }

    @Override
    public Object getDetailAttribute() {
        return AttrTargetKeyReferenced.builder().target(repository).key(key).referenced(referenced).build();
    }
}
