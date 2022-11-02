package com.adplatform.restApi.global.config.security.exception;

public class ExpiredAccessTokenException extends SecurityException {
    private static final String CODE_KEY = "expiredAccessTokenException.code";
    private static final String MESSAGE_KEY = "expiredAccessTokenException.message";

    public ExpiredAccessTokenException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
