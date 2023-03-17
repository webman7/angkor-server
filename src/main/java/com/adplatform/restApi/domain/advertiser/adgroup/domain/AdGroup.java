package com.adplatform.restApi.domain.advertiser.adgroup.domain;

import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author junny
 * @since 1.0
 */
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
        /** 수동 입찰 */
        MANUAL,

        /** 자동 입찰 */
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
        /** 운영 */
        ON,
        /** 관리자 정지 */
        ADMIN_STOP,
        /** 연결 서비스 제한 */
        EXTERNAL_SERVICE_STOP
    }

    /**
     * 광고 그룹 상태
     */
    public enum Status {
        /** 집행 예정 */
        READY,
        /** 집행 중 */
        LIVE,
        /** 집행 완료 */
        FINISHED,
        /** 사용자 OFF */
        OFF,
        /** 삭제 */
        DELETED,
        /** 관리자 정지 */
        ADMIN_STOP,
        /** 캠페인 일 예산 초과 */
        EXCEED_DAILY_BUDGET,
        /** 일시중지 */
        PAUSED,
        /** 집행 가능한 소재가 없음 */
        NO_AVAILABLE_CREATIVE,
        /** 계약해지 */
        CANCELED,
        /** 연결 서비스 제한으로 운영불가인 상태 */
        SYSTEM_CONFIG_EXTERNAL_SERVICE_STOP,
        /** 광고계정 운영불가 */
        ADACCOUNT_UNAVAILABLE
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
    private final Set<Media> media = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "adgroup_device_info",
            joinColumns = @JoinColumn(name = "adgroup_info_id"),
            inverseJoinColumns = @JoinColumn(name = "device_info_id"))
    private final Set<Device> devices = new HashSet<>();

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

    /** 수동 입찰 금액 */
    @Column(name = "bid_amount", columnDefinition = "INT")
    private Long bidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "bid_strategy", length = 10)
    private BidStrategy bidStrategy;

    /** 일일 예산 */
    @Column(name = "daily_budget_amount", columnDefinition = "INT")
    private Long dailyBudgetAmount;

    /** 총 예산 */
    @Column(name = "budget_amount", columnDefinition = "INT")
    private Long budgetAmount;

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
    @Column(name = "config", columnDefinition = "VARCHAR(5)")
    private Config config;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_config", length = 20)
    private SystemConfig systemConfig;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private Status status;

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
            Long budgetAmount,
            boolean fullDeviceDisplay,
            boolean onlyWifiDisplay,
            boolean allMedia,
            boolean onlyAdult,
            Config config,
            SystemConfig systemConfig,
            Status status) {
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
        this.budgetAmount = budgetAmount;
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

    public void update(AdGroupDto.Request.Update request, List<Media> media, List<Device> devices) {
        this.demographicTarget.update(request.getDemographicTarget());
        this.adGroupSchedule.update(request.getAdGroupSchedule());
        this.media.addAll(media);
        this.devices.addAll(devices);
        this.name = request.getName();
        this.pacing = request.getPacing();
        this.pacingType = request.getPacingType();
        this.bidAmount = request.getBidAmount();
        this.bidStrategy = request.getBidStrategy();
        this.dailyBudgetAmount = request.getDailyBudgetAmount();
        this.budgetAmount = request.getBudgetAmount();
        this.fullDeviceDisplay = request.isFullDeviceDisplay();
        this.onlyWifiDisplay = request.isOnlyWifiDisplay();
        this.allMedia = request.isAllMedia();
        this.onlyAdult = request.isOnlyAdult();
    }

    public void delete() {
        this.status = Status.DELETED;
        this.config = Config.DEL;
        this.creatives.forEach(Creative::delete);
    }

    public AdGroup copy(AdGroupDto.Request.Copy request, Campaign campaign) {
        AdGroup copy = new AdGroup();
        copy.campaign = campaign;
        copy.demographicTarget = this.demographicTarget.copy(copy);
        copy.adGroupSchedule = this.adGroupSchedule.copy(copy);
        copy.media.addAll(this.media);
        copy.devices.addAll(this.devices);
        copy.name = this.name;
        copy.pacing = this.pacing;
        copy.pacingType = this.pacingType;
        copy.bidAmount = this.bidAmount;
        copy.bidStrategy = this.bidStrategy;
        copy.dailyBudgetAmount = this.dailyBudgetAmount;
        copy.budgetAmount = this.budgetAmount;
        copy.fullDeviceDisplay = this.fullDeviceDisplay;
        copy.onlyWifiDisplay = this.onlyWifiDisplay;
        copy.allMedia = this.allMedia;
        copy.onlyAdult = this.onlyAdult;
        copy.config = this.config;
        copy.systemConfig = this.systemConfig;
        copy.status = this.status;

        if (request.isChangeStartEndDate())
            copy.adGroupSchedule.updateStartEndDate(request.getStartDate(), request.getEndDate());

        if (!request.isOnlyAdGroup()) {
            copy.creatives.addAll(this.creatives.stream()
                    .filter(c -> c.getReviewStatus().equals(Creative.ReviewStatus.APPROVED))
                    .map(c -> c.copy(copy))
                    .collect(Collectors.toList()));
        }

        return copy;
    }

    public void changeConfigOn() {
        this.config = Config.ON;
    }

    public void changeConfigOff() {
        this.config = Config.OFF;
    }

    public void changeStatusOff() {
        this.status = Status.OFF;
    }

    public void changeStatusReady() {
        this.status = Status.READY;
    }

    public void changeStatusLive() {
        this.status = Status.LIVE;
    }

    public void changeStatusFinished() {
        this.status = Status.FINISHED;
    }
}
