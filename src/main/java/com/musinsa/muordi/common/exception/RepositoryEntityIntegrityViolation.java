package com.musinsa.muordi.common.exception;

import lombok.Getter;

@Getter
public class RepositoryEntityIntegrityViolation extends RepositoryException {
    protected final Object key;
    protected final String action;

    protected RepositoryEntityIntegrityViolation(String repository, Object key, String action, int code) {
        super(repository, code);

        this.key = key;
        this.action = action;
    }

    public RepositoryEntityIntegrityViolation(String repository, String action, Object key) {
        this("Repository " + repository + " 의 key=" + key + "를 " + action + " 하는것은 데이터 무결성을 해칩니다.", key, action,103);
    }
}
