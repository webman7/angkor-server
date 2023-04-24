package com.adplatform.restApi.domain.statistics.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

/**
 * @author junny
 * @since 1.0
 */
@Getter
public class ReportInformationResponse {
    private final int cost;
    private final int impression;
    private final int click;
    private final int reach;
    private final int videoAutoPlay;
    private final int videoTouches;
    private final int videoUnmute;
    private final int videoPlay3Seconds;
    private final int videoPlay25Percent;
    private final int videoPlay50Percent;
    private final int videoPlay75Percent;
    private final int videoPlay100Percent;

    @QueryProjection
    public ReportInformationResponse(
            int cost,
            int impression,
            int click,
            int reach,
            int videoAutoPlay,
            int videoTouches,
            int videoUnmute,
            int videoPlay3Seconds,
            int videoPlay25Percent,
            int videoPlay50Percent,
            int videoPlay75Percent,
            int videoPlay100Percent) {
        this.cost = cost;
        this.impression = impression;
        this.click = click;
        this.reach = reach;
        this.videoAutoPlay = videoAutoPlay;
        this.videoTouches = videoTouches;
        this.videoUnmute = videoUnmute;
        this.videoPlay3Seconds = videoPlay3Seconds;
        this.videoPlay25Percent = videoPlay25Percent;
        this.videoPlay50Percent = videoPlay50Percent;
        this.videoPlay75Percent = videoPlay75Percent;
        this.videoPlay100Percent = videoPlay100Percent;
    }
}
