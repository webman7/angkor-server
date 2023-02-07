package com.adplatform.restApi.statistics.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public enum ReportIndicator {
    COST("cost"),
    IMPRESSION("impression"),
    CLICK("click"),
    REACH("reach"),
    VIDEO_PLAY_3_SECONDS("videoPlay3Seconds"),
    SIGNUP_DAY_1("signUpDay1"),
    SIGNUP_DAY_7("signUpDay7"),
    PURCHASE_DAY_1("purchaseDay1"),
    PURCHASE_DAY_7("purchaseDay7"),
    VIEW_CART_DAY_1("viewCartDay1"),
    VIEW_CART_DAY_7("viewCartDay7"),
    CTR("ctr"),
    CPM("cpm"),
    CPC("cpc"),
    REACH_RATE("reachRate"),
    VIDEO_PLAY_RATE("videoPlayRate");

    private final String value;
}
