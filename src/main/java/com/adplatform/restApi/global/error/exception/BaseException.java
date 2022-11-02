package com.adplatform.restApi.global.error.exception;

public abstract class BaseException extends RuntimeException {
    private String codeKey;
    private String messageKey;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BaseException(String codeKey, String messageKey) {
        this.codeKey = codeKey;
        this.messageKey = messageKey;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
