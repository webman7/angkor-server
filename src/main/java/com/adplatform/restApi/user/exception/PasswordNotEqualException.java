package com.adplatform.restApi.user.exception;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class PasswordNotEqualException extends UserException {
    private static final String CODE_KEY = "passwordNotEqualException.code";
    private static final String MESSAGE_KEY = "passwordNotEqualException.message";

    public PasswordNotEqualException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
