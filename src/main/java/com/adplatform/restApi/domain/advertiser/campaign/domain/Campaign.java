package com.adplatform.restApi.domain.advertiser.campaign.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author junny
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

    @Column(name = "budget_amount", columnDefinition = "INT")
    private Long budgetAmount;

    @Column(name = "start_date")
    private Integer startDate;

    @Column(name = "end_date", columnDefinition = "int DEFAULT 29991231")
    private Integer endDate;

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
            Integer startDate,
            Integer endDate,
            Long dailyBudgetAmount,
            Long budgetAmount,
            GoalType goalType,
            String trackingId,
            TrackingType trackingType,
            Config config,
            SystemConfig systemConfig,
            Status status) {
        this.adTypeAndGoal = adTypeAndGoal;
        this.adAccount = adAccount;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyBudgetAmount = dailyBudgetAmount;
        this.budgetAmount = budgetAmount;
        this.goalType = goalType;
        this.trackingId = trackingId;
        this.trackingType = trackingType;
        this.config = config;
        this.systemConfig = systemConfig;
        this.status = status;
    }

    public Campaign update(CampaignDto.Request.Update request) {
        this.name = request.getName();
        this.goalType = request.getGoalType();
        this.trackingId = request.getTrackingId();
        this.trackingType = request.getTrackingType();
        return this;
    }

    public Campaign updateBudget(CampaignDto.Request.Update request) {
        this.dailyBudgetAmount = request.getDailyBudgetAmount();
        this.budgetAmount = request.getBudgetAmount();
        return this;
    }

    public void delete() {
        this.status = Status.DELETED;
        this.config = Config.DEL;
        this.adGroups.forEach(AdGroup::delete);
    }

    public void saveStartEndDate(CampaignDto.Response.ForDateSave request) {
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }

    public void updateStartEndDate(CampaignDto.Response.ForDateUpdate request) {
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
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

    public void changeAdminStopOn() {
        this.systemConfig = SystemConfig.ADMIN_STOP;
        this.config = Config.OFF;
        this.status = Status.ADMIN_STOP;
    }
    public void changeAdminStopOff() { this.systemConfig = SystemConfig.ON; }
}
