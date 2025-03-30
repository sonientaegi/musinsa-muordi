package com.musinsa.muordi.common.exception;

public class DummyException extends RuntimeException {
    public DummyException() {
        super();
    }

    public DummyException(String message) {
        super(message);
    }

    public DummyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DummyException(Throwable cause) {
        super(cause);
    }

    protected DummyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
