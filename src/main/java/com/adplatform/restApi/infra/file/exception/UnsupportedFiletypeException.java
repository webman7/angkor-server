package com.adplatform.restApi.infra.file.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author junny
 * @since 1.0
 */
public class UnsupportedFiletypeException extends BaseException {
    private static final String CODE_KEY = "unsupportedFiletypeException.code";
    private static final String MESSAGE_KEY = "unsupportedFiletypeException.message";

    public UnsupportedFiletypeException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
