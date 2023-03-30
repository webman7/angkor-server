package com.adplatform.restApi.domain.advertiser.creative.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CreativeReviewAlreadyRejectException extends BaseException {
    private static final String CODE_KEY = "creativeReviewAlreadyRejectException.code";
    private static final String MESSAGE_KEY = "creativeReviewAlreadyRejectException.message";

    public CreativeReviewAlreadyRejectException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
