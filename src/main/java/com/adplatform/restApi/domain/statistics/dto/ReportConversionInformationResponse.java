package com.adplatform.restApi.domain.statistics.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

/**
 * @author junny
 * @since 1.0
 */
@Getter
public class ReportConversionInformationResponse {
    private int signUpDay1;
    private int signUpDay7;
    private int purchaseDay1;
    private int purchaseDay7;
    private int viewCartDay1;
    private int viewCartDay7;

    @QueryProjection
    public ReportConversionInformationResponse(
            int signUpDay1,
            int signUpDay7,
            int purchaseDay1,
            int purchaseDay7,
            int viewCartDay1,
            int viewCartDay7) {
        this.signUpDay1 = signUpDay1;
        this.signUpDay7 = signUpDay7;
        this.purchaseDay1 = purchaseDay1;
        this.purchaseDay7 = purchaseDay7;
        this.viewCartDay1 = viewCartDay1;
        this.viewCartDay7 = viewCartDay7;
    }
}
