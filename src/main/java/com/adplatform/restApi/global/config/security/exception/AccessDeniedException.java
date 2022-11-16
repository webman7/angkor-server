package com.adplatform.restApi.global.config.security.exception;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AccessDeniedException extends SecurityException {
    private static final String CODE_KEY = "accessDeniedException.code";
    private static final String MESSAGE_KEY = "accessDeniedException.message";

    public AccessDeniedException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
