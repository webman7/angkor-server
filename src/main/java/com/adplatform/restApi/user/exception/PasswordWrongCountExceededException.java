package com.adplatform.restApi.user.exception;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class PasswordWrongCountExceededException extends UserException {
    private static final String CODE_KEY = "passwordWrongCountExceededException.code";
    private static final String MESSAGE_KEY = "passwordWrongCountExceededException.message";

    public PasswordWrongCountExceededException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
