package com.musinsa.muordi;

import com.musinsa.muordi.common.exception.BaseException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MuordiExceptionController {
    @Getter
    @Builder
    static class BaseErrorResponse<T> {
        int code;
        T message;
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handle(BaseException e) {
        return ResponseEntity.status(e.getHttpStatusCode()).body(BaseErrorResponse.builder().code(e.getCode()).message(e.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseErrorResponse.builder().code(-1).message(e).build());
    }
}
