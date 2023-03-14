package com.adplatform.restApi.domain.history.domain.mediaCompany.user;

import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.global.entity.HistoryCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "media_company_user_info_history")
public class MediaCompanyUserInfoHistory extends HistoryCreatedEntity {
    @Column(name = "company_info_id", nullable = false)
    private Integer companyId;

    @Column(name = "user_no")
    private Integer userNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", length = 10)
    private MediaCompanyUser.MemberType memberType;

    @Enumerated(EnumType.STRING)
    @Column(name = "accounting_yn", nullable = false, columnDefinition = "CHAR(1)")
    private MediaCompanyUser.AccountingYN accountingYN;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private MediaCompanyUser.Status status;

    @Column(name = "reg_user_no")
    private Integer regUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "reg_date")
    private LocalDateTime createdAt;

    @Column(name = "upd_user_no")
    private Integer updUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;

    @Builder
    public MediaCompanyUserInfoHistory(
            Integer companyId,
            Integer userNo,
            MediaCompanyUser.MemberType memberType,
            MediaCompanyUser.AccountingYN accountingYN,
            MediaCompanyUser.Status status,
            Integer regUserNo,
            LocalDateTime createdAt,
            Integer updUserNo,
            LocalDateTime updatedAt) {
        this.companyId = companyId;
        this.userNo = userNo;
        this.memberType = memberType;
        this.accountingYN = accountingYN;
        this.status = status;
        this.regUserNo = regUserNo;
        this.createdAt = createdAt;
        this.updUserNo = updUserNo;
        this.updatedAt = updatedAt;
    }
}
