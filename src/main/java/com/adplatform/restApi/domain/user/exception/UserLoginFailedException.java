package com.adplatform.restApi.domain.user.exception;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class UserLoginFailedException extends UserException {
    private static final String CODE_KEY = "userLoginFailedException.code";
    private static final String MESSAGE_KEY = "userLoginFailedException.message";

    public UserLoginFailedException() { super(CODE_KEY, MESSAGE_KEY); }
}
