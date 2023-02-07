package com.adplatform.restApi.domain.advertiser.adgroup.converter;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class TimeBooleanListToStringConverter implements AttributeConverter<List<Boolean>, String> {
    @Override
    public String convertToDatabaseColumn(List<Boolean> attribute) {
        return attribute.stream().map(a -> a ? "1" : "0").collect(Collectors.joining(""));
    }

    @Override
    public List<Boolean> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(""))
                .map(d -> d.equals("1") ? Boolean.TRUE : Boolean.FALSE)
                .collect(Collectors.toList());
    }
}
