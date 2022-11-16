package com.adplatform.restApi.global.config.security.exception;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class ExpiredAccessTokenException extends SecurityException {
    private static final String CODE_KEY = "expiredAccessTokenException.code";
    private static final String MESSAGE_KEY = "expiredAccessTokenException.message";

    public ExpiredAccessTokenException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
