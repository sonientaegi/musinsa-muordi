package com.musinsa.muordi.platform.domain.category;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryDTOTest {
    @Test
    void toStringTest() {
        CategoryDTO dto = new CategoryDTO(1, "CATEGORY 1", 1);
        assertEquals("TEST=CategoryDTO(id=1, name=CATEGORY 1, displaySequence=1)", "TEST=%s".formatted(dto));
    }

}