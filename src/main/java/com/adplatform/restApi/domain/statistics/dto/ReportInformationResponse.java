package com.adplatform.restApi.domain.statistics.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
public class ReportInformationResponse {
    private int cost;
    private int impression;
    private int click;
    private int reach;
    private int videoAutoPlay;
    private int videoTouches;
    private int videoUnmute;
    private int videPlay3Seconds;
    private int videPlay5Seconds;
    private int videPlay7Seconds;
    private int videPlay15Seconds;
    private int videPlay30Seconds;
    private int videPlay60Seconds;
    private int videPlay25Percent;
    private int videPlay50Percent;
    private int videPlay75Percent;
    private int videPlay100Percent;

    @QueryProjection
    public ReportInformationResponse(
            int cost,
            int impression,
            int click,
            int reach,
            int videoAutoPlay,
            int videoTouches,
            int videoUnmute,
            int videPlay3Seconds,
            int videPlay5Seconds,
            int videPlay7Seconds,
            int videPlay15Seconds,
            int videPlay30Seconds,
            int videPlay60Seconds,
            int videPlay25Percent,
            int videPlay50Percent,
            int videPlay75Percent,
            int videPlay100Percent) {
        this.cost = cost;
        this.impression = impression;
        this.click = click;
        this.reach = reach;
        this.videoAutoPlay = videoAutoPlay;
        this.videoTouches = videoTouches;
        this.videoUnmute = videoUnmute;
        this.videPlay3Seconds = videPlay3Seconds;
        this.videPlay5Seconds = videPlay5Seconds;
        this.videPlay7Seconds = videPlay7Seconds;
        this.videPlay15Seconds = videPlay15Seconds;
        this.videPlay30Seconds = videPlay30Seconds;
        this.videPlay60Seconds = videPlay60Seconds;
        this.videPlay25Percent = videPlay25Percent;
        this.videPlay50Percent = videPlay50Percent;
        this.videPlay75Percent = videPlay75Percent;
        this.videPlay100Percent = videPlay100Percent;
    }
}
