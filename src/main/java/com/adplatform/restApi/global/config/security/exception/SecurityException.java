package com.adplatform.restApi.global.config.security.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class SecurityException extends BaseException {
    public SecurityException() {
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SecurityException(String codeKey, String messageKey) {
        super(codeKey, messageKey);
    }
}
