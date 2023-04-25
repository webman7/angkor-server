package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_master_detail")
public class WalletMasterDetail extends BaseCreatedEntity {

    @Column(name = "business_account_info_id")
    private Integer businessAccountId;
    @Column(name = "available_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float availableAmount;
    @Column(name = "total_reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float totalReserveAmount;
    @Column(name = "change_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float changeAmount;
    @Column(name = "change_reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float changeReserveAmount;
    @Column(name = "change_available_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float changeAvailableAmount;
    @Column(name = "change_total_reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float changeTotalReserveAmount;
    @Column(name = "summary", length = 40)
    private String summary;
    @Column(name = "memo", length = 1000)
    private String memo;

    @Builder
    public WalletMasterDetail(Integer businessAccountId, Float availableAmount, Float totalReserveAmount, Float changeAmount, Float changeReserveAmount, Float changeAvailableAmount, Float changeTotalReserveAmount, String summary, String memo) {
        this.businessAccountId = businessAccountId;
        this.availableAmount = availableAmount;
        this.totalReserveAmount = totalReserveAmount;
        this.changeAmount = changeAmount;
        this.changeReserveAmount = changeReserveAmount;
        this.changeAvailableAmount = changeAvailableAmount;
        this.changeTotalReserveAmount = changeTotalReserveAmount;
        this.summary = summary;
        this.memo = memo;
    }
}
