package com.adplatform.restApi.user.exception;

public class RejectUserException extends UserException {
    private static final String CODE_KEY = "rejectUserException.code";
    private static final String MESSAGE_KEY = "rejectUserException.message";

    public RejectUserException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}

