package com.musinsa.muordi.contents.explore.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.muordi.common.util.NumberWithCommasSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class NumberWithCommasSerializerTest {
    static class AmountPack {
        public String name;
        @JsonSerialize(using = NumberWithCommasSerializer.class)
        public int amountInt;
        @JsonSerialize(using = NumberWithCommasSerializer.class)
        public long amountLong;
    }


    @Test
    public void decimal() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AmountPack pack = new AmountPack();
        pack.amountInt = 99999;
        pack.amountLong = 4444444444L;
        pack.name = "hellospring";

        String expected = """
                {"name":"hellospring","amountInt":"99,999","amountLong":"4,444,444,444"}""";
        String actual = mapper.writeValueAsString(pack);

    }
}