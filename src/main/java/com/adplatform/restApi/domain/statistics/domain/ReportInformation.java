package com.adplatform.restApi.domain.statistics.domain;

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
public class ReportInformation {
    @Column(name = "cost")
    private int cost;

    @Column(name = "impression")
    private int impression;

    @Column(name = "click")
    private int click;

    @Column(name = "reach")
    private int reach;

    @Column(name = "video_play_auto")
    private int videoAutoPlay;

    @Column(name = "video_play_touch")
    private int videoTouches;

    @Column(name = "video_unmute")
    private int videoUnmute;

    @Column(name = "video_play_3s")
    private int videPlay3Seconds;

    @Column(name = "video_play_5s")
    private int videPlay5Seconds;

    @Column(name = "video_play_10s")
    private int videPlay7Seconds;

    @Column(name = "video_play_15s")
    private int videPlay15Seconds;

    @Column(name = "video_play_30s")
    private int videPlay30Seconds;

    @Column(name = "video_play_60s")
    private int videPlay60Seconds;

    @Column(name = "video_play_25p")
    private int videPlay25Percent;

    @Column(name = "video_play_50p")
    private int videPlay50Percent;

    @Column(name = "video_play_75p")
    private int videPlay75Percent;

    @Column(name = "video_play_100p")
    private int videPlay100Percent;
}
