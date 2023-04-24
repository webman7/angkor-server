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

    @Column(name = "summary", length = 40)
    private String summary;

    @Builder
    public WalletMasterDetail(Integer businessAccountId, Float availableAmount, Float totalReserveAmount, String summary) {
        this.businessAccountId = businessAccountId;
        this.availableAmount = availableAmount;
        this.totalReserveAmount = totalReserveAmount;
        this.summary = summary;
    }
}
