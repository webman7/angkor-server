package com.adplatform.restApi.domain.advertiser.campaign.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CampaignNotFoundException extends BaseException {
    private static final String CODE_KEY = "campaignNotFoundException.code";
    private static final String MESSAGE_KEY = "campaignNotFoundException.message";

    public CampaignNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
