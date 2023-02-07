package com.adplatform.restApi.agency.businessright.domain;

import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_business_right_request")
public class BusinessRightRequest extends BaseUpdatedEntity {

    // 요청(REQUESTED), 수락(COMPLETED), 실패(FAILED), 거절(REJECTED), 해제(RELEASED), 이관(TRANSFERRED)
    public enum Status {
        REQUESTED, COMPLETED, FAILED, REJECTED, RELEASED, TRANSFERRED
    }

    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "company_info_id")
    private Integer companyId;

    @Column(name = "request_company_info_id")
    private Integer requestCompanyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status;

    @Builder
    public BusinessRightRequest(Integer adAccountId, Integer companyId, Integer requestCompanyId, Status status) {
        this.adAccountId = adAccountId;
        this.companyId = companyId;
        this.requestCompanyId = requestCompanyId;
        this.status = status;
    }

    public BusinessRightRequest update(BusinessRightDto.Request.SaveStatus request) {
        this.status = BusinessRightRequest.Status.valueOf(request.getStatus());
        return this;
    }
}
