package com.adplatform.restApi.advertiser.adaccount.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_business_right_info")
public class AdAccountBusinessRight extends BaseCreatedEntity {
    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "company_info_id")
    private Integer companyId;

    @Column(name = "start_date")
    private Integer startDate;

    @Column(name = "end_date")
    private Integer endDate;

    @Builder
    public AdAccountBusinessRight(Integer adAccountId, Integer companyId, Integer startDate, Integer endDate) {
        this.adAccountId = adAccountId;
        this.companyId = companyId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
