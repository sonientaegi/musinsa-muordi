package com.musinsa.muordi.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 숫자를 3자리 마다 콤마가 찍힌 문자로 변환해주는 시리얼라이저이다. 어노테이션으로 사용할 수 있다.<br><br>
 * <pre>
 * {@code
 * @JsonSerialize(using = NumberWithCommasSerializer.class)
 * private long longAmount;
 *
 * @JsonSerialize(using = NumberWithCommasSerializer.class)
 * private int intAmount;
 * }
 * </pre>
 */
public class NumberWithCommasSerializer extends JsonSerializer<Object> {
    private DecimalFormat df = new DecimalFormat("###,###");

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(this.df.format(value));
    }
}
