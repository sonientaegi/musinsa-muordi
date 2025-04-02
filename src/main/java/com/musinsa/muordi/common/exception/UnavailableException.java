package com.musinsa.muordi.common.exception;

public class UnavailableException extends BaseException {
    public UnavailableException(String message) {
        super(message);
    }

    public UnavailableException() {
        super("요청한 자원이 일시적으로 제공 불가합니다");
    }
}
