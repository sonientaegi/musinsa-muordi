package com.musinsa.muordi.common.exception;

import lombok.Getter;

@Getter
public class RepositoryEntityDuplicatedException extends RepositoryException {
    protected final Object key;

    protected RepositoryEntityDuplicatedException(String repository, Object key, int code) {
        super(repository, code);

        this.key = key;
    }

    public RepositoryEntityDuplicatedException(String repository, Object key) {
        this("Repository " + repository + " 의 key=" + key + " 레고드가 존재합니다.", key, 102);
    }
}
