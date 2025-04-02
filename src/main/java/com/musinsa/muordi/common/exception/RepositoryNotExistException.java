package com.musinsa.muordi.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.muordi.common.util.ExceptionController;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
