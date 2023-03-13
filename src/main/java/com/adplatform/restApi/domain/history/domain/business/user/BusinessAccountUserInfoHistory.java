package com.adplatform.restApi.domain.history.domain.business.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.global.entity.HistoryCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "business_account_user_info_history")
public class BusinessAccountUserInfoHistory extends HistoryCreatedEntity {
    @Column(name = "business_account_info_id", nullable = false)
    private Integer businessAccountId;

    @Column(name = "user_no")
    private Integer userNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", length = 10)
    private BusinessAccountUser.MemberType memberType;

    @Enumerated(EnumType.STRING)
    @Column(name = "accounting_yn", nullable = false, columnDefinition = "CHAR(1)")
    private BusinessAccountUser.AccountingYN accountingYN;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private BusinessAccountUser.Status status;

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
    public BusinessAccountUserInfoHistory(
            Integer businessAccountId,
            Integer userNo,
            BusinessAccountUser.MemberType memberType,
            BusinessAccountUser.AccountingYN accountingYN,
            BusinessAccountUser.Status status,
            Integer regUserNo,
            LocalDateTime createdAt,
            Integer updUserNo,
            LocalDateTime updatedAt) {
        this.businessAccountId = businessAccountId;
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
