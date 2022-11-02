package com.adplatform.restApi.global.converter;

import javax.persistence.AttributeConverter;
import java.util.List;

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
