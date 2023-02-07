package com.adplatform.restApi.user.exception;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class UserNotFoundException extends UserException {
    private static final String CODE_KEY = "userNotFoundException.code";
    private static final String MESSAGE_KEY = "userNotFoundException.message";

    public UserNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
