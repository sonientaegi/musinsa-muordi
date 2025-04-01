package com.musinsa.muordi.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
public class BaseException extends RuntimeException {
    private final HttpStatusCode httpStatusCode;
    protected final int code;

    public BaseException(String message, HttpStatusCode httpStatusCode, int code) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.code = code;
    }

    public HttpStatusCode getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public BaseException() {
        this("알 수 없는 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, -1);
    }

    public BaseException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, -1);
    }

    public BaseException(String message, int code) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, code);
    }

    public BaseException(String message, HttpStatus httpStatusCode) {
        this(message, httpStatusCode, -1);
    }
}
