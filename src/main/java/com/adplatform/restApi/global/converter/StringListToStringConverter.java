package com.adplatform.restApi.global.converter;

import javax.persistence.AttributeConverter;
import java.util.List;

/**
 * String List to String or String to String List Converter Class
 *
 * <p>{@code String}으로 이루어진 {@code List}를 단순 {@code String}으로 변환한다. e.g. {"a", "b", "c"} -> "a,b,c"
 * <p>쉼표(,)로 구분되어진 {@code String}을 {@code List}로 변환한다. e.g. "a,b,c" -> {"a", "b", "c"}
 * @author junny
 * @since 1.0
 * @see javax.persistence.Convert Convert
 */
public class StringListToStringConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(",", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return List.of(dbData.split(","));
    }
}
