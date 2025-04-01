package com.musinsa.muordi.platform.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BrandResponse {
    private final int id;
    private final String name;
}
