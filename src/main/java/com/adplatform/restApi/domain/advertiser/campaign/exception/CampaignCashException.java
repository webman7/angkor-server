package com.adplatform.restApi.domain.advertiser.campaign.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CampaignCashException extends BaseException {
    private static final String CODE_KEY = "campaignCashException.code";
    private static final String MESSAGE_KEY = "campaignCashException.message";

    public CampaignCashException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
