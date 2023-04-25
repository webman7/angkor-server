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
    private Integer businessAccountId;

    @Column(name = "summary", length = 40)
    private String summary;
    @Column(name = "change_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float changeAmount;
    @Column(name = "available_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float availableAmount;
    @Column(name = "change_available_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float changeAvailableAmount;
    @Column(name = "memo", length = 1000)
    private String memo;

    @Column(name = "wallet_charge_log_id")
    private Integer walletChargeLogId;

    @Column(name = "wallet_auto_charge_log_id")
    private Integer walletAutoChargeLogId;

    @Column(name = "wallet_refund_id")
    private Integer walletRefundId;


    @Builder
    public WalletLog(Integer businessAccountId, String summary, Float changeAmount, Float availableAmount, Float changeAvailableAmount, String memo, Integer walletChargeLogId, Integer walletAutoChargeLogId, Integer walletRefundId) {
        this.businessAccountId = businessAccountId;
        this.summary = summary;
        this.changeAmount = changeAmount;
        this.availableAmount = availableAmount;
        this.changeAvailableAmount = changeAvailableAmount;
        this.memo = memo;
        this.walletChargeLogId = walletChargeLogId;
        this.walletAutoChargeLogId = walletAutoChargeLogId;
        this.walletRefundId = walletRefundId;
    }
}
