package com.adplatform.restApi.global.config.security.exception;

/**
 * @author junny
 * @since 1.0
 */
public class AuthenticationEntryPointException extends SecurityException {
    private static final String CODE_KEY = "authenticationEntrypointException.code";
    private static final String MESSAGE_KEY = "authenticationEntrypointException.message";

    public AuthenticationEntryPointException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
