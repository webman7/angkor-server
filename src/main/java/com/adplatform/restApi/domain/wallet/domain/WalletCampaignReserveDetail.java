package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_campaign_reserve_detail")
public class WalletCampaignReserveDetail extends BaseCreatedEntity {
    public enum Fluctuation {
        /** 증가 */
        P,
        /** 감소 */
        M
    }

    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "campaign_info_id")
    private Integer campaignId;

    @Enumerated(EnumType.STRING)
    @Column(name = "fluctuation", columnDefinition = "CHAR")
    private Fluctuation fluctuation;

    @Column(name = "summary", length = 40)
    private String summary;

    @Column(name = "change_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float changeAmount;

    @Column(name = "reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float reserveAmount;

    @Column(name = "reserve_change_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float reserveChangeAmount;

    @Builder
    public WalletCampaignReserveDetail (
            Integer businessAccountId,
            Integer adAccountId,
            Integer campaignId,
            Fluctuation fluctuation,
            String summary,
            Float changeAmount,
            Float reserveAmount,
            Float reserveChangeAmount
    ) {
        this.businessAccountId = businessAccountId;
        this.adAccountId = adAccountId;
        this.campaignId = campaignId;
        this.fluctuation = fluctuation;
        this.summary = summary;
        this.changeAmount = changeAmount;
        this.reserveAmount = reserveAmount;
        this.reserveChangeAmount = reserveChangeAmount;
    }
}
