package com.adplatform.restApi.domain.history.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "campaign_budget_change_history")
public class CampaignBudgetChangeHistory  extends BaseEntity {

    @Column(name = "business_account_info_id", nullable = false)
    private Integer businessAccountId;

    @Column(name = "adaccount_info_id", nullable = false)
    private Integer adAccountId;

    @Column(name = "campaign_info_id", nullable = false)
    private Integer campaignId;

    @Column(name = "chg_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float chgAmount;

    @Column(name = "available_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float availableAmount;

    @Column(name = "available_chg_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float availableChgAmount;

    @Column(name = "reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float reserveAmount;

    @Column(name = "reserve_chg_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float reserveChgAmount;

    @CreatedBy
    @Column(name = "reg_user_no")
    private Integer createdUserNo;

    @Builder
    public CampaignBudgetChangeHistory(
            Integer businessAccountId,
            Integer adAccountId,
            Integer campaignId,
            Float chgAmount,
            Float availableAmount,
            Float availableChgAmount,
            Float reserveAmount,
            Float reserveChgAmount,
            Integer createdUserNo) {
        this.businessAccountId = businessAccountId;
        this.adAccountId = adAccountId;
        this.campaignId = campaignId;
        this.chgAmount = chgAmount;
        this.availableAmount = availableAmount;
        this.availableChgAmount = availableChgAmount;
        this.reserveAmount = reserveAmount;
        this.reserveChgAmount = reserveChgAmount;
        this.createdUserNo = createdUserNo;
    }
}
