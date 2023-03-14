package com.adplatform.restApi.domain.company.domain;

import com.adplatform.restApi.domain.user.domain.User;
import lombok.AccessLevel;
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

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "media_company_user_info")
public class MediaCompanyUser {
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
        C,
        /** 삭제 */
        D
    }

    @EmbeddedId
    private final MediaCompanyUserId id = new MediaCompanyUserId();

    @MapsId("companyId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_info_id")
    private Company company;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", length = 10)
    private MediaCompanyUser.MemberType memberType;

    @Enumerated(EnumType.STRING)
    @Column(name = "accounting_yn", nullable = false, columnDefinition = "CHAR(1)")
    private MediaCompanyUser.AccountingYN accountingYN;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private MediaCompanyUser.Status status;

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
    public MediaCompanyUser(Company company, User user, MediaCompanyUser.MemberType memberType, MediaCompanyUser.AccountingYN accountingYN, MediaCompanyUser.Status status) {
        this.company = company;
        this.user = user;
        this.memberType = memberType;
        this.accountingYN = accountingYN;
        this.status = status;
        this.id.setCompanyId(company.getId());
        this.id.setUserId(user.getId());
    }


    public void changeStatusY() {
        this.status = MediaCompanyUser.Status.Y;
    }
    public void changeStatusR() {
        this.status = MediaCompanyUser.Status.R;
    }
    public void changeStatusC() {
        this.status = MediaCompanyUser.Status.C;
    }

    public void changeMemberTypeMaster() {
        this.memberType = MediaCompanyUser.MemberType.MASTER;
    }
    public void changeMemberTypeOperator() {
        this.memberType = MediaCompanyUser.MemberType.OPERATOR;
    }
    public void changeMemberTypeMember() {
        this.memberType = MediaCompanyUser.MemberType.MEMBER;
    }
}
