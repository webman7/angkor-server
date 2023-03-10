package com.adplatform.restApi.domain.advertiser.campaign.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author junny
 * @since 1.0
 */
public class AdTypeAndGoalNotFoundException extends BaseException {
    private static final String CODE_KEY = "adTypeAndGoalNotFoundException.code";
    private static final String MESSAGE_KEY = "adTypeAndGoalNotFoundException.message";

    public AdTypeAndGoalNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
