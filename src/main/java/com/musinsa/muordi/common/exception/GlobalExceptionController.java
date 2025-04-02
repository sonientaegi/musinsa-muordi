package com.musinsa.muordi.common.exception;

import com.musinsa.muordi.common.util.ExceptionController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * {@inheritDoc}
 * 서비스의 모든 Custom Exception 을 처리한다.
 */
@RestControllerAdvice
public class GlobalExceptionController extends ExceptionController {
    /**
     * <h>500</h><br><br>
     * Repository 동작중 발생한 오류를 처리한다. CUD 하기 전 데이터 정합성에 대한 최소한의 확인을 Service에서 수행해야하며
     * DB 처리 중 발생한 실제 오류 : 정합성 위배, 락 경합, 수행 불가 쿼리 등에 대해서 예외로 처리한다.
     * @param exception RepositoryException 을 상속받은 모든 예외.
     * @param request HTTP 요청.
     * @return 예외 응답.
     */
    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<Object> repositoryExceptionHandler(RepositoryException exception, WebRequest request) {
        return this.makeErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * <h>404</h><br><br>
     * 리소스를 요청하였으나 발견할 수 없는 경우를 처리한다. R을 수행하였으나 없는경우에 해당한다. 이 오류는 시스템의 오류도, 사용자의 오류도 아닌
     * 단지 리소스가 없는 상황을 의미한다.
     * @param exception NotFoundException 를 상속받은 모든 예외.
     * @param request HTTP 요청.
     * @return 예외 응답.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(NotFoundException exception, WebRequest request) {
        return this.makeErrorResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    /**
     * <h>400</h><br><br>
     * 해당 작업을 이미 수행하여 실행할 수 없는 경우를 처리한다. 같인 상품을 두개의 카테고리 등록하려한다든지, 이미 삭제한 상품을 다시 삭제하려 할때
     * 발생하는 예외에 대해서 400으로 응답한다.
     * @param exception AlreadyDoneException 를 상속받은 모든 예외.
     * @param request HTTP 요청.
     * @return 예외 응답.
     */
    @ExceptionHandler(AlreadyDoneException.class)
    public ResponseEntity<Object> alreadyDoneExceptionHandler(AlreadyDoneException exception, WebRequest request) {
        return this.makeErrorResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * <h>503</h><br><br>
     * 데이터를 제공하지 못하는 상황이다. 전시 중인 제품이 하나도 없거나, 전시 필수 조건을 만족하지 못하는 등의 무결성 문제가 있는 경우
     * 505 오류를 반환한다.
     * @param exception UnavailableException 을 상속받은 모든 예외.
     * @param request HTTP 요청.
     * @return 예외 응답.
     */
    @ExceptionHandler(UnavailableException.class)
    public ResponseEntity<Object> repositoryExceptionHandler(UnavailableException exception, WebRequest request) {
        return this.makeErrorResponse(exception, HttpStatus.SERVICE_UNAVAILABLE, request);
    }
}
