package com.musinsa.muordi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

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
