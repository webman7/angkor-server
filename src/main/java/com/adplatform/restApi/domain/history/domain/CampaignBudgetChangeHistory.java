package com.adplatform.restApi.domain.history.domain;

import com.adplatform.restApi.domain.adgroup.domain.*;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.wallet.domain.Cash;
import com.adplatform.restApi.domain.wallet.domain.WalletLogId;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "campaign_budget_change_history")
public class CampaignBudgetChangeHistory  extends BaseEntity {

    @Column(name = "adaccount_info_id", nullable = false)
    private Integer adAccountId;

    @Column(name = "campaign_info_id", nullable = false)
    private Integer campaignId;

    @Column(name = "cash_info_id")
    private Integer cashId;

    @Column(name = "chg_amount", columnDefinition = "INT")
    private Long chgAmount;

    @Column(name = "available_amount", columnDefinition = "INT")
    private Long availableAmount;

    @Column(name = "available_chg_amount", columnDefinition = "INT")
    private Long availableChgAmount;

    @Column(name = "reserve_amount", columnDefinition = "INT")
    private Long reserveAmount;

    @Column(name = "reserve_chg_amount", columnDefinition = "INT")
    private Long reserveChgAmount;

    @CreatedBy
    @Column(name = "reg_user_no")
    private Integer createdUserId;

    @Builder
    public CampaignBudgetChangeHistory(
            Integer adAccountId,
            Integer campaignId,
            Integer cashId,
            Long chgAmount,
            Long availableAmount,
            Long availableChgAmount,
            Long reserveAmount,
            Long reserveChgAmount,
            Integer createdUserId) {
        this.adAccountId = adAccountId;
        this.campaignId = campaignId;
        this.cashId = cashId;
        this.chgAmount = chgAmount;
        this.availableAmount = availableAmount;
        this.availableChgAmount = availableChgAmount;
        this.reserveAmount = reserveAmount;
        this.reserveChgAmount = reserveChgAmount;
        this.createdUserId = createdUserId;
    }
}
