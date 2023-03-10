package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_log")
public class WalletLog extends BaseCreatedEntity {

    @Column(name = "business_account_info_id")
    private int businessAccountId;

    @Column(name = "adaccount_info_id")
    private int adAccountId;

    @Column(name = "summary", length = 40)
    private String summary;

    @Column(name = "out_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float outAmount;

    @Column(name = "in_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float inAmount;

    @Column(name = "memo", length = 100)
    private String memo;

    @Column(name = "wallet_charge_log_id")
    private int walletChargeLogId;

    @Column(name = "wallet_auto_charge_log_id")
    private int walletAutoChargeLogId;

    @Column(name = "wallet_refund_id")
    private int walletRefundId;


    @Builder
    public WalletLog(Integer businessAccountId, Integer adAccountId, String summary, Float outAmount, Float inAmount, String memo, Integer walletChargeLogId, Integer walletAutoChargeLogId, Integer walletRefundId) {
        this.businessAccountId = businessAccountId;
        this.adAccountId = adAccountId;
        this.summary = summary;
        this.outAmount = outAmount;
        this.inAmount = inAmount;
        this.memo = memo;
        this.walletChargeLogId = walletChargeLogId;
        this.walletAutoChargeLogId = walletAutoChargeLogId;
        this.walletRefundId = walletRefundId;
    }
}
