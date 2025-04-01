package com.musinsa.muordi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException() {
        super("리소스가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }

    public HttpStatusCode getHttpStatusCode() {
        return super.getHttpStatusCode();
    }
}
