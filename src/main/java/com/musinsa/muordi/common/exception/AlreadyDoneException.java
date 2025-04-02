package com.musinsa.muordi.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.muordi.common.util.ExceptionController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 해당 작업을 이미 수행하여 중복 수행할 수 없다.
 */
public class AlreadyDoneException extends BaseException implements ExceptionController.Attributer {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Action {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        protected final Object target;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        protected final String action;
    }

    protected final Object target;
    protected final String action;

    public AlreadyDoneException(Object target, String action) {
        super("%s 에 대한 %s 을(를) 이미 수행하여, 중복수행 할 수 없습니다".formatted(target, action));
        this.target = target;
        this.action = action;
    }

    public AlreadyDoneException() {
        super("해당 작업을 이미 수행하였습니다");
        this.target = null;
        this.action = null;
    }

    @Override
    public Object getDetailAttribute() {
        return new Action(this.target, this.action);
    }
}
