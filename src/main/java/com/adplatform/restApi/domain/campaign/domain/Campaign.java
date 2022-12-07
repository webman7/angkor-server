package com.adplatform.restApi.domain.campaign.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "campaign_info")
public class Campaign extends BaseUpdatedEntity {
    /**
     * 광고 목표 타입.
     */
    public enum GoalType {
        MATOMO_AND_SDK
    }

    /**
     * 전환 추적 타입.
     */
    public enum TrackingType {
        PURCHASE, APP_INSTALL, SIGNUP, POTENTIAL_CUSTOMER, APPLY_SERVICE, BASKET
    }

    /**
     * 캠페인 상태.
     */
    public enum Config {
        ON, OFF, DEL
    }

    /**
     * 캠페인 시스템 상태.
     */
    public enum SystemConfig {
        ON, ADMIN_STOP, EXTERNAL_SERVICE_STOP
    }

    /**
     * 캠페인 상태.
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_type_goal_info_id")
    private AdTypeAndGoal adTypeAndGoal;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AdGroup> adGroups = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "adaccount_info_id")
    private AdAccount adAccount;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "daily_budget_amount", columnDefinition = "INT")
    private Long dailyBudgetAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type", length = 20)
    private GoalType goalType;

    @Column(name = "tracking_id", length = 45)
    private String trackingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_type", length = 45)
    private TrackingType trackingType;

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
    public Campaign(
            AdTypeAndGoal adTypeAndGoal,
            AdAccount adAccount,
            String name,
            Long dailyBudgetAmount,
            GoalType goalType,
            String trackingId,
            TrackingType trackingType,
            Config config,
            SystemConfig systemConfig,
            Status status) {
        this.adTypeAndGoal = adTypeAndGoal;
        this.adAccount = adAccount;
        this.name = name;
        this.dailyBudgetAmount = dailyBudgetAmount;
        this.goalType = goalType;
        this.trackingId = trackingId;
        this.trackingType = trackingType;
        this.config = config;
        this.systemConfig = systemConfig;
        this.status = status;
    }

    public Campaign update(CampaignDto.Request.Update request) {
        this.name = request.getName();
        this.dailyBudgetAmount = request.getDailyBudgetAmount();
        this.goalType = request.getGoalType();
        this.trackingId = request.getTrackingId();
        this.trackingType = request.getTrackingType();
        return this;
    }

    public void delete() {
        this.status = Status.DELETED;
        this.config = Config.DEL;
        this.adGroups.forEach(AdGroup::delete);
    }

    public void changeConfigOn() {
        this.config = Config.ON;
    }

    public void changeConfigOff() {
        this.config = Config.OFF;
    }
}
