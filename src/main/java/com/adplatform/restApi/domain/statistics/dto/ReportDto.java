package com.adplatform.restApi.domain.statistics.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

/**
 * @author junny
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
        private float ctr;
        private int cpm;
        private int cpc;
        private float reachRate;
        private float videoPlayRate;
    }

    @Getter
    @Setter
    public static class MediaResponse {
        private int impression;
        private int click;
        private float ctr;
    }
}
