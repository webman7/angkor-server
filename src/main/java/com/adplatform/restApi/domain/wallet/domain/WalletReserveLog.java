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
@Table(name = "wallet_reserve_log")
public class WalletReserveLog extends BaseEntity {

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

    @Column(name = "total_reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float totalReserveAmount;

    @Column(name = "reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float reserveAmount;

    @Column(name = "reserve_vat_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float reserveVatAmount;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;

    @Builder
    public WalletReserveLog (
            Integer businessAccountId,
            Integer adAccountId,
            Integer campaignId,
            Fluctuation fluctuation,
            Float totalReserveAmount,
            Float reserveAmount,
            Float reserveVatAmount
    ) {
        this.businessAccountId = businessAccountId;
        this.adAccountId = adAccountId;
        this.campaignId = campaignId;
        this.fluctuation = fluctuation;
        this.totalReserveAmount = totalReserveAmount;
        this.reserveAmount = reserveAmount;
        this.reserveVatAmount = reserveVatAmount;
        this.updatedAt = LocalDateTime.now();
    }
}
