package com.musinsa.muordi.platform.domain.brand;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandDTOTest {
    @Test
    void toStringTest() {
        BrandDTO dto = new BrandDTO(1, "BRAND 1");
        assertEquals("TEST=BrandDTO(id=1, name=BRAND 1)", "TEST=%s".formatted(dto));
    }

}