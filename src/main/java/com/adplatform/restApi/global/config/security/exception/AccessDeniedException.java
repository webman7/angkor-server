package com.adplatform.restApi.global.config.security.exception;

public class AccessDeniedException extends SecurityException {
    private static final String CODE_KEY = "accessDeniedException.code";
    private static final String MESSAGE_KEY = "accessDeniedException.message";

    public AccessDeniedException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
