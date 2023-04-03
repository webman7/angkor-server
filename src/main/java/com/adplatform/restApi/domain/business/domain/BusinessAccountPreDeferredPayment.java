package com.adplatform.restApi.domain.business.domain;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "business_account_pre_deferred_payment_info")
@DynamicInsert
public class BusinessAccountPreDeferredPayment extends BaseCreatedEntity {

    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "pre_payment_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean prePayment;

    @Column(name = "start_date", columnDefinition = "INT")
    private Integer startDate;

    @Column(name = "end_date", columnDefinition = "int DEFAULT 29991231")
    private Integer endDate;

    @Builder
    public BusinessAccountPreDeferredPayment(
            Integer businessAccountId,
            boolean prePayment,
            Integer startDate) {
        this.businessAccountId = businessAccountId;
        this.prePayment = prePayment;
        this.startDate = startDate;
    }
}
