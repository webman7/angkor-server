package com.adplatform.restApi.domain.adgroup.domain;

import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adgroup_info")
public class AdGroup extends BaseUpdatedEntity {
    /**
     * 게재 방식
     */
    public enum Pacing {
        NONE, QUICK, NORMAL
    }

    /**
     * 과금 방식
     */
    public enum PacingType {
        CPM, CPC, CPA, CPV
    }

    /**
     * 입찰 방식
     */
    public enum BidStrategy {
        /**
         * 수동 입찰
         */
        MANUAL,

        /**
         * 자동 입찰
         */
        AUTO
    }

    /**
     * 광고 그룹 상태
     */
    public enum Config {
        ON, OFF, DEL
    }

    /**
     * 광고 그룹 시스템 상태
     */
    public enum SystemConfig {
        /**
         * 운영
         */
        ON,
        /**
         * 관리자 정지
         */
        ADMIN_STOP,
        /**
         * 연결 서비스 제한
         */
        EXTERNAL_SERVICE_STOP
    }

    @Setter
    @ManyToOne
    @JoinColumn(name = "campaign_info_id")
    private Campaign campaign;

    @OneToOne(mappedBy = "adGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private AdGroupDemographicTarget demographicTarget;

    @OneToOne(mappedBy = "adGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private AdGroupSchedule adGroupSchedule;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "adgroup_media_info",
            joinColumns = @JoinColumn(name = "adgroup_info_id"),
            inverseJoinColumns = @JoinColumn(name = "media_info_id"))
    private final List<Media> media = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "adgroup_device_info",
            joinColumns = @JoinColumn(name = "adgroup_info_id"),
            inverseJoinColumns = @JoinColumn(name = "device_info_id"))
    private final List<Device> devices = new ArrayList<>();

    @OneToMany(mappedBy = "adGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Creative> creatives = new ArrayList<>();

    @Column(name = "name", length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "pacing", length = 10)
    private Pacing pacing;

    @Enumerated(EnumType.STRING)
    @Column(name = "pacing_type", length = 6)
    private PacingType pacingType;

    /**
     * 수동 입찰 금액
     */
    @Column(name = "bid_amount", columnDefinition = "INT")
    private Long bidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "bid_strategy", length = 10)
    private BidStrategy bidStrategy;

    /**
     * 일일 예산
     */
    @Column(name = "daily_budget_amount", columnDefinition = "INT")
    private Long dailyBudgetAmount;

    /**
     * 전체 디바이스 노출 여부.<br/>
     * {@link Boolean#TRUE true}: 전체 디바이스 노출.<br/>
     * {@link Boolean#FALSE false}: 일부 디바이스 노출.
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "device_all_yn", columnDefinition = "CHAR")
    private boolean fullDeviceDisplay;

    /**
     * WIFI 노출 여부<br/>
     * {@link Boolean#TRUE true}: WIFI 상태에서만 노출<br/>
     * {@link Boolean#FALSE false}: WIFI 상태가 아닌 상태에서도 노출
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "use_wifi_only_yn", columnDefinition = "CHAR")
    private boolean onlyWifiDisplay;

    /**
     * 전체 미디어 노출 여부<br/>
     * {@link Boolean#TRUE true}: 전체 미디어 노출<br/>
     * {@link Boolean#FALSE false}: 일부 미디어 노출
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "media_all_yn", columnDefinition = "CHAR")
    private boolean allMedia;

    /**
     * 성인 타게팅 여부<br/>
     * {@link Boolean#TRUE true}: 20세 이상에게만 노출<br/>
     * {@link Boolean#FALSE false}: 연령 무관 노출
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "adult_yn", columnDefinition = "CHAR")
    private boolean onlyAdult;

    @Enumerated(EnumType.STRING)
    @Column(name = "config", columnDefinition = "CHAR(5)")
    private Config config;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_config", length = 20)
    private SystemConfig systemConfig;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private Campaign.Status status;

    @Builder
    public AdGroup(
            Campaign campaign,
            AdGroupDemographicTarget demographicTarget,
            AdGroupSchedule adGroupSchedule,
            List<Media> media,
            List<Device> devices,
            String name,
            Pacing pacing,
            PacingType pacingType,
            Long bidAmount,
            BidStrategy bidStrategy,
            Long dailyBudgetAmount,
            boolean fullDeviceDisplay,
            boolean onlyWifiDisplay,
            boolean allMedia,
            boolean onlyAdult,
            Config config,
            SystemConfig systemConfig,
            Campaign.Status status) {
        this.campaign = campaign;
        this.demographicTarget = demographicTarget;
        this.adGroupSchedule = adGroupSchedule;
        this.media.addAll(media);
        this.devices.addAll(devices);
        this.name = name;
        this.pacing = pacing;
        this.pacingType = pacingType;
        this.bidAmount = bidAmount;
        this.bidStrategy = bidStrategy;
        this.dailyBudgetAmount = dailyBudgetAmount;
        this.fullDeviceDisplay = fullDeviceDisplay;
        this.onlyWifiDisplay = onlyWifiDisplay;
        this.allMedia = allMedia;
        this.onlyAdult = onlyAdult;
        this.config = config;
        this.systemConfig = systemConfig;
        this.status = status;
        this.injectThis();
    }

    private void injectThis() {
        if (this.demographicTarget != null) this.demographicTarget.setAdGroup(this);
        if (this.adGroupSchedule != null) this.adGroupSchedule.setAdGroup(this);
    }

    public AdGroup addMedia(List<Media> media) {
        media.forEach(m -> {
            if (!this.media.contains(m)) this.media.add(m);
        });
        return this;
    }

    public AdGroup addDevice(List<Device> devices) {
        devices.forEach(d -> {
            if (!this.devices.contains(d)) this.devices.add(d);
        });
        return this;
    }
}
