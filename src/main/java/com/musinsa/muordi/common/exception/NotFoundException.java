package com.musinsa.muordi.common.exception;

import com.musinsa.muordi.common.util.ExceptionController;

/**
 * 조회 하고자 하는 자원이 없다.
 */
public class NotFoundException extends BaseException implements ExceptionController.Attributer {
    protected final String repository;
    protected final Object key;

    public NotFoundException(String repository, Object key) {
        super("조회 또는 참조하려는 %s 의 %s 가 존재하지 않습니다".formatted(repository, key));
        this.repository = repository;
        this.key = key;
    }

    public NotFoundException() {
        super("조회 또는 참조하려는 자원이 존재하지 않습니다");
        this.repository = null;
        this.key = null;
    }

    @Override
    public Object getDetailAttribute() {
        return AttrTargetKeyReferenced.builder().target(repository).key(key).build();
    }
}
