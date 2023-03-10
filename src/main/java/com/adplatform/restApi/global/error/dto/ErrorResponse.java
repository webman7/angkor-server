package com.adplatform.restApi.global.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int errorCode;
    private final String errorMessage;
    private Map<String, String> errors;

    private ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private ErrorResponse(int errorCode, String errorMessage, Map<String, String> errors) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

    public static ErrorResponse create(int errorCode, String errorMessage) {
        return new ErrorResponse(errorCode, errorMessage);
    }

    public static ErrorResponse create(int errorCode, String errorMessage, Map<String, String> errors) {
        return new ErrorResponse(errorCode, errorMessage, errors);
    }
}
