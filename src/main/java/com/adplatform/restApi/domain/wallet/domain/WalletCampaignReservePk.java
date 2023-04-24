package com.adplatform.restApi.domain.wallet.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class WalletCampaignReservePk implements Serializable {
    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "campaign_info_id")
    private Integer campaignId;
}
