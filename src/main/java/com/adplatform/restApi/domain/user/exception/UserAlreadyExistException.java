package com.adplatform.restApi.domain.user.exception;

/**
 * @author junny
 * @since 1.0
 */
public class UserAlreadyExistException extends UserException {
    private static final String CODE_KEY = "userAlreadyExistException.code";
    private static final String MESSAGE_KEY = "userAlreadyExistException.message";

    public UserAlreadyExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
