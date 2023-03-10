package com.adplatform.restApi.domain.business.domain;

import com.adplatform.restApi.domain.business.exception.BusinessAccountUserAuthorizationException;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "business_account_user_info")
public class BusinessAccountUser {
    public enum MemberType {
        MASTER,
        OPERATOR,
        MEMBER
    }

    public enum AccountingYN {
        /** 회계권한 (비즈니스계정당 한명만 가능) */
        Y,
        /** 권한없음 */
        N
    }

    public enum Status {
        /** 수락 */
        Y,
        /** 요청 */
        N,
        /** 거부 */
        R,
        /** 요청 취소 */
        C
    }

    @EmbeddedId
    private final BusinessAccountUserId id = new BusinessAccountUserId();

    @MapsId("businessAccountId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_account_info_id")
    private BusinessAccount businessAccount;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", length = 10)
    private MemberType memberType;

    @Enumerated(EnumType.STRING)
    @Column(name = "accounting_yn", nullable = false, columnDefinition = "CHAR(1)")
    private AccountingYN accountingYN;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private Status status;

    @CreatedBy
    @Column(name = "reg_user_no")
    private Integer createdUserNo;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "upd_user_no")
    private Integer updatedUserNo;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;

    @Builder
    public BusinessAccountUser(BusinessAccount businessAccount, User user, MemberType memberType, AccountingYN accountingYN, Status status) {
        this.businessAccount = businessAccount;
        this.user = user;
        this.memberType = memberType;
        this.accountingYN = accountingYN;
        this.status = status;
        this.id.setBusinessAccountId(businessAccount.getId());
        this.id.setUserId(user.getId());
    }

    public void validateStatus() {
        if (!this.status.equals(Status.Y)) throw new BusinessAccountUserAuthorizationException();
    }
}
