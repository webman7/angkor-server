package com.adplatform.restApi.domain.agency.businessright.domain;

import com.adplatform.restApi.domain.agency.businessright.dto.BusinessRightDto;
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
public class BusinessRight extends BaseCreatedEntity {
    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "company_info_id")
    private Integer companyId;

    @Column(name = "start_date")
    private Integer startDate;

    @Column(name = "end_date")
    private Integer endDate;

    @Column(name = "request_user_no")
    private Integer requestUserNo;

    @Builder
    public BusinessRight(Integer adAccountId, Integer companyId, Integer startDate, Integer endDate, Integer requestUserNo) {
        this.adAccountId = adAccountId;
        this.companyId = companyId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestUserNo = requestUserNo;
    }

    public BusinessRight update(BusinessRightDto.Request.Save request) {
        this.endDate = request.getEndDate();
        return this;
    }
}