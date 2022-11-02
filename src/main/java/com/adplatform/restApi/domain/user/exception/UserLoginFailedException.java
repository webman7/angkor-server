package com.adplatform.restApi.domain.user.exception;

public class UserLoginFailedException extends UserException {
    private static final String CODE_KEY = "userLoginFailedException.code";
    private static final String MESSAGE_KEY = "userLoginFailedException.message";

    public UserLoginFailedException() { super(CODE_KEY, MESSAGE_KEY); }
}
