package com.musinsa.muordi.contents.display.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ShowcaseCreateRequest {
    private long productId;
    private int categoryId;
}

