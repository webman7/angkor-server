package com.adplatform.restApi.domain.user.exception;

/**
 * @author junny
 * @since 1.0
 */
public class UserNotFoundException extends UserException {
    private static final String CODE_KEY = "userNotFoundException.code";
    private static final String MESSAGE_KEY = "userNotFoundException.message";

    public UserNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
