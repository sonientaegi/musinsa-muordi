package com.musinsa.muordi.common.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.muordi.common.exception.BaseException;
import com.musinsa.muordi.common.exception.RepositoryException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;

/**
 * 예외 처리 구현을 위한 Controller 이다. 직접 RestControllerAdvice 로 등록하는것을 막기 위해 추상 클래스로 선언한다.
 * Custom Exception 의 응답에 detail attribute를 기록하려면 Custom Exception 에 {@link Attributer}
 * 인터페이스를 상속받아 구현하고, ExceptionController를 상속받은 구현체에 예외 핸들러를 구현한다.
 * 오류메시지는 {@link ExceptionController#makeErrorResponse} 를 호출하여 생성한다.
 */
public abstract class ExceptionController {
    // API Error 구조체를 대체할 내부 클래스.
    @Builder
    @Getter
    private static class ApiError {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private final ZonedDateTime timestamp;
        private final int status;
        private final String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final Object detail;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String trace;
        private final String path;
    }

    /**
     * GlobalExceptionController 를 통한 예외 처리시 "detail" 필드에 값을 전달하려면 Attributer 인터페이스를 상속받은 예외 클래스를 구현한다.
     */
    public interface Attributer {
        /**
         * 예외처리 응답 생성 시 detail 항목에 추가할 attribute를 반환한다.
         * @return
         */
        Object getDetailAttribute();
    }

    // inclued-stacktrace 가 always 일때만 스텍트레이스를 남긴다.
    // param 일때 어떻게 하는지 향후 확인 필요.
    @Value("${server.error.include-stacktrace:never}")
    private String printStackTrace;

    /**
     * 에러 응답을 생성한다.
     * @param exception 에러를 출력할 예외.
     * @param httpStatus HTTP 응답.
     * @param request HTTP 요청.
     * @return 예외를 위한 HTTP 응답.
     */
    protected ResponseEntity<Object> makeErrorResponse(Exception exception,
                                                     HttpStatus httpStatus,
                                                     WebRequest request) {
        // uri 요청을 찾은다음 접두사 "uri=" 를 제거한다.
        String path = request.getDescription(false);
        if (path.startsWith("uri=")) {
            path = path.substring("uri=".length());
        }

        // inclued-stacktrace=always인 경우 StackTrace를 출력한다.
        String stackTrace = null;
        if (printStackTrace.equals("always")) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            stackTrace = sw.toString();
        }

        // detail에 특성을 추가한다.
        Object attribute = null;
        if (exception instanceof Attributer) {
            attribute = ((Attributer) exception).getDetailAttribute();
        }

        // 에러 응답 생성.
        ApiError error = ApiError.builder()
                .timestamp(ZonedDateTime.now())
                .status(httpStatus.value())
                .message(exception.getMessage())
                .detail(attribute)
                .trace(stackTrace)
                .path(path)
                .build();
        return ResponseEntity.status(httpStatus).body(error);
    }

    /**
     * <h>500</h><br><br>
     * 별도로 명시하지 않은 모든 사용자 오류는 일괄적으로 500 으로 응답한다.
     * @param exception 위에서 처리하지 못한 모든 BaseException 을 상속받은 모든 예외.
     * @param request HTTP 요청.
     * @return 예외 응답.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> repositoryExceptionHandler(BaseException exception, WebRequest request) {
        return this.makeErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
