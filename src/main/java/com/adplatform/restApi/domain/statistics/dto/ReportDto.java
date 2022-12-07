package com.adplatform.restApi.domain.statistics.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public abstract class ReportDto {
    @Getter
    @Setter
    public static class Request {
        @Valid
        List<ReportCondition> reportConditions;
    }

    @Getter
    @Setter
    public static class Response {
        private int cost;
        private int impression;
        private int click;
        private int reach;
        private int videoAutoPlay;
        private int videoTouches;
        private int videoUnmute;
        private int videoPlay3Seconds;
        private int videoPlay5Seconds;
        private int videoPlay10Seconds;
        private int videoPlay15Seconds;
        private int videoPlay30Seconds;
        private int videoPlay60Seconds;
        private int videoPlay25Percent;
        private int videoPlay50Percent;
        private int videoPlay75Percent;
        private int videoPlay100Percent;
        private int signUpDay1;
        private int signUpDay7;
        private int purchaseDay1;
        private int purchaseDay7;
        private int viewCartDay1;
        private int viewCartDay7;
        private int ctr;
        private int cpm;
        private int cpc;
        private int reachRate;
        private int videoPlayRate;
    }
}
