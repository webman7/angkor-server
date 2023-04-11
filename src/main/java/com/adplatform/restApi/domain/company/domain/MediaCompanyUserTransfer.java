package com.adplatform.restApi.domain.company.domain;

import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "media_company_user_transfer_info")
public class MediaCompanyUserTransfer extends BaseCreatedEntity {
    @Column(name = "company_info_id")
    private Integer companyId;

    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "transfer_user_no")
    private Integer transferUserNo;

    @Builder
    public MediaCompanyUserTransfer(Integer companyId, Integer userNo, Integer transferUserNo) {
        this.companyId = companyId;
        this.userNo = userNo;
        this.transferUserNo = transferUserNo;
    }
}
