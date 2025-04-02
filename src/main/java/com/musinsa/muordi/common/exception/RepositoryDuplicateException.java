package com.musinsa.muordi.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.muordi.common.util.ExceptionController;
import lombok.Getter;

/**
 * 데이터 중복 발생.
 */
@Getter
public class RepositoryDuplicateException extends RepositoryException implements ExceptionController.Attributer {
    @JsonProperty
    protected final Object key;

    public RepositoryDuplicateException(String repository, Object key) {
        super(repository, "저장하려는 %s 의 %s 가 이미 존재합니다.".formatted(repository, key));
        this.key = key;
    }

    @Override
    public Object getDetailAttribute() {
        return AttrTargetKeyReferenced.builder().target(repository).key(key).build();
    }
}
