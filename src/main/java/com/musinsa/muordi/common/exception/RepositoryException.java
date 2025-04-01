package com.musinsa.muordi.common.exception;

import lombok.Getter;

// TODO RepositoryException 상속 시 메시지 출력 방식 수정. toString Override?
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
