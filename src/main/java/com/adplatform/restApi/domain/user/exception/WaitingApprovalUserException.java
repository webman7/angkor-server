package com.adplatform.restApi.domain.user.exception;

public class WaitingApprovalUserException extends UserException {
    private static final String CODE_KEY = "waitingApprovalUserException.code";
    private static final String MESSAGE_KEY = "waitingApprovalUserException.message";

    public WaitingApprovalUserException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
