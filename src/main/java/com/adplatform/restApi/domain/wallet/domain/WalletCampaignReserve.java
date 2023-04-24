package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_campaign_reserve_info")
public class WalletCampaignReserve {

    @EmbeddedId
    private final WalletCampaignReservePk id = new WalletCampaignReservePk();

    @Column(name = "reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float reserveAmount;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;

    @Builder
    public WalletCampaignReserve (
            Integer businessAccountId,
            Integer adAccountId,
            Integer campaignId,
            Float reserveAmount
    ) {
        this.id.setBusinessAccountId(businessAccountId);
        this.id.setAdAccountId(adAccountId);
        this.id.setCampaignId(campaignId);
        this.reserveAmount = reserveAmount;
        this.updatedAt = LocalDateTime.now();
    }
}
