package com.adplatform.restApi.advertiser.user.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
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
