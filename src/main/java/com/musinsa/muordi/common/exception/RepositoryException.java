package com.musinsa.muordi.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.muordi.common.util.ExceptionController;
import lombok.Getter;

/**
 * 데이터 접근 중 오류 발생
 */
@Getter
public class RepositoryException extends BaseException implements ExceptionController.Attributer {
    protected final String repository;

    public RepositoryException(String repository) {
        super("Repository " + repository + " 접근 중 오류가 발생했습니다.");
        this.repository = repository;
    }

    public RepositoryException(String repository,  String message) {
        super(message);
        this.repository = repository;
    }

    @Override
    public Object getDetailAttribute() {
        return AttrTargetKeyReferenced.builder().target(repository).build();
    }
}
