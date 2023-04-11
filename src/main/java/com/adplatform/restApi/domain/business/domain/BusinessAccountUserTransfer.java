package com.adplatform.restApi.domain.business.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "business_user_transfer_info")
public class BusinessAccountUserTransfer extends BaseCreatedEntity {
    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "transfer_user_no")
    private Integer transferUserNo;

    @Builder
    public BusinessAccountUserTransfer(Integer businessAccountId, Integer userNo, Integer transferUserNo) {
        this.businessAccountId = businessAccountId;
        this.userNo = userNo;
        this.transferUserNo = transferUserNo;
    }
}