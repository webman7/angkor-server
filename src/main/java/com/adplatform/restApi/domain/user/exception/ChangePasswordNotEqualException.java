package com.adplatform.restApi.domain.user.exception;

public class ChangePasswordNotEqualException extends UserException {
    private static final String CODE_KEY = "changePasswordNotEqualException.code";
    private static final String MESSAGE_KEY = "changePasswordNotEqualException.message";

    public ChangePasswordNotEqualException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
