package com.musinsa.muordi.common.exception;

import lombok.Getter;

/**
 * DB 억세스 도중 데이터 누락 발생.
 */
@Getter
public class RepositoryEntityNotExistException extends RepositoryException {
    protected final Object key;

    protected RepositoryEntityNotExistException(String repository, Object key, int code) {
        super(repository, code);

        this.key = key;
    }

    public RepositoryEntityNotExistException(String repository, Object key) {
        this("Repository " + repository + " 의 key=" + key + " 레코드가 없습니다.", key, 101);
    }
}
