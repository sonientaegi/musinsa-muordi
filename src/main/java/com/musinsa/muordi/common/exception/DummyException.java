package com.musinsa.muordi.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DummyException extends RuntimeException {
    public DummyException(Throwable cause) {
        super(cause);
    }

    public DummyException(String message) {
        super(message);
    }
}
