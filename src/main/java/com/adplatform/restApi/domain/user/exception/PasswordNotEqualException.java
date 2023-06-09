package com.adplatform.restApi.domain.user.exception;

/**
 * @author junny
 * @since 1.0
 */
public class PasswordNotEqualException extends UserException {
    private static final String CODE_KEY = "passwordNotEqualException.code";
    private static final String MESSAGE_KEY = "passwordNotEqualException.message";

    public PasswordNotEqualException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
