package com.adplatform.restApi.domain.statistics.domain.settlement;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_settlement")
public class AdAccountSettlement extends BaseEntity {

    @Column(name = "business_account_info_id")
    private int businessAccountId;

    @Column(name = "adaccount_info_id")
    private int adAccountId;

    @Column(name = "stat_date")
    private int statDate;

    @Column(name = "supply_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float supplyAmount;

    @Column(name = "vat_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float vatAmount;

    @Column(name = "total_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float totalAmount;

    @Builder
    public AdAccountSettlement(
            Integer businessAccountId,
            Integer adAccountId,
            Integer statDate,
            Float supplyAmount,
            Float vatAmount,
            Float totalAmount) {
        this.businessAccountId = businessAccountId;
        this.adAccountId = adAccountId;
        this.statDate = statDate;
        this.supplyAmount = supplyAmount;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
    }
}
