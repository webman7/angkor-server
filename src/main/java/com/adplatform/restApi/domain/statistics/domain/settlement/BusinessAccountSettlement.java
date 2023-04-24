package com.adplatform.restApi.domain.statistics.domain.settlement;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "business_account_settlement")
public class BusinessAccountSettlement extends BaseEntity {

    @Column(name = "business_account_info_id")
    private int businessAccountId;

    @Column(name = "stat_date")
    private int statDate;

    @Column(name = "supply_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float supplyAmount;

    @Builder
    public BusinessAccountSettlement(
            Integer businessAccountId,
            Integer statDate,
            Float supplyAmount) {
        this.businessAccountId = businessAccountId;
        this.statDate = statDate;
        this.supplyAmount = supplyAmount;
    }
}
