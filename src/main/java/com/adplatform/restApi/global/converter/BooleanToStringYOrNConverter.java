package com.adplatform.restApi.global.converter;

import javax.persistence.AttributeConverter;

/**
 * Boolean to String Y, N or String Y, N to Boolean Converter Class.
 *
 * <p>{@code Boolean}을 {@code String} Y 또는 N으로 변환하거나,
 * <p>{@code String} Y 또는 N을 {@code Boolean} {@code true} 또는 {@code false}로 변환하는 컨버터 클래스이다.
 *
 * @author junny
 * @since 1.0
 * @see javax.persistence.Convert Convert
 */
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
