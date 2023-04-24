package com.adplatform.restApi.domain.statistics.domain.report;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ReportInformation {
    @Column(name = "cost", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private float cost;

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
    private int videoPlay3Seconds;

    @Column(name = "video_play_25p")
    private int videoPlay25Percent;

    @Column(name = "video_play_50p")
    private int videoPlay50Percent;

    @Column(name = "video_play_75p")
    private int videoPlay75Percent;

    @Column(name = "video_play_100p")
    private int videoPlay100Percent;
}
