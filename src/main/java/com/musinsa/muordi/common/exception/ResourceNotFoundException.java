package com.musinsa.muordi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 조회 하고자 하는 자원이 없음.
 */
public class ResourceNotFoundException extends BaseException {
    protected final String detailMessage;

    public ResourceNotFoundException(String detailMessage) {
        super("리소스가 존재하지 않습니다" + (detailMessage == null ? "." : " (" + detailMessage + ")."), HttpStatus.NOT_FOUND);
        this.detailMessage = detailMessage;
    }

    public ResourceNotFoundException() {
        this(null);
    }
}
