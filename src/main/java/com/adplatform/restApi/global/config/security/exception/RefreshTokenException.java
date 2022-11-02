package com.adplatform.restApi.global.config.security.exception;

public class RefreshTokenException extends SecurityException {
    private static final String CODE_KEY = "refreshTokenInValidException.code";
    private static final String MESSAGE_KEY = "refreshTokenInValidException.message";

    public RefreshTokenException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
