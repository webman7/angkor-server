package com.adplatform.restApi.domain.batch.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class BatchStatusYNException extends BaseException {
    private static final String CODE_KEY = "batchStatusYNException.code";
    private static final String MESSAGE_KEY = "batchStatusYNException.message";

    public BatchStatusYNException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}

