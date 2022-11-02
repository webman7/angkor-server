package com.adplatform.restApi.domain.user.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class UserException extends BaseException {
    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UserException(String codeKey, String messageKey) {
        super(codeKey, messageKey);
    }
}
