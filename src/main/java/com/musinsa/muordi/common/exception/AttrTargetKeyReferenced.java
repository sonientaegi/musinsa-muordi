package com.musinsa.muordi.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class AttrTargetKeyReferenced {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String target;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object key;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object referenced;
}
