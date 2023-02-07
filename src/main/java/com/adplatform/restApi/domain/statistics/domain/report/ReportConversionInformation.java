package com.adplatform.restApi.domain.statistics.domain.report;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ReportConversionInformation {
    @Column(name = "signup_1d")
    private int signUpDay1;

    @Column(name = "signup_7d")
    private int signUpDay7;

    @Column(name = "purchase_1d")
    private int purchaseDay1;

    @Column(name = "purchase_7d")
    private int purchaseDay7;

    @Column(name = "view_cart_1d")
    private int viewCartDay1;

    @Column(name = "view_cart_7d")
    private int viewCartDay7;
}
