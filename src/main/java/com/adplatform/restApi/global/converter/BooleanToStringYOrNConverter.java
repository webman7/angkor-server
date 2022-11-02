package com.adplatform.restApi.global.converter;

import javax.persistence.AttributeConverter;

public class BooleanToStringYOrNConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) return null;
        if (attribute) return "Y";
        return "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if(dbData == null) return null;
        if (dbData.equals("Y") || dbData.equals("y")) return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
