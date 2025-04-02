package com.musinsa.muordi.common.exception;

import lombok.Getter;

/**
 * DB 억세스 도중 예외 발생.
 */
@Getter
public class RepositoryException extends BaseException {
    protected final String repository;

    protected RepositoryException(String repository, int code) {
        super("Repository " + repository + " 접근 중 오류가 발생했습니다.", code);
        this.repository = repository;
    }

    public RepositoryException(String repository) {
        this(repository, 100);
    }
}
