package com.adplatform.restApi.infra.file.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class UnsupportedFiletypeException extends BaseException {
    private static final String CODE_KEY = "unsupportedFiletypeException.code";
    private static final String MESSAGE_KEY = "unsupportedFiletypeException.message";

    public UnsupportedFiletypeException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
