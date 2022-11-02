package com.adplatform.restApi.domain.user.exception;

public class WithdrawUserException extends UserException {
    private static final String CODE_KEY = "withdrawUserException.code";
    private static final String MESSAGE_KEY = "withdrawUserException.message";

    public WithdrawUserException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
