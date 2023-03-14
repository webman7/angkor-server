package com.adplatform.restApi.domain.adaccount.domain;

import com.adplatform.restApi.domain.adaccount.exception.AdAccountUserAuthorizationException;
import com.adplatform.restApi.domain.user.domain.User;
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
 * @author junny
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "adaccount_user_info")
public class AdAccountUser {
    public enum MemberType {
        MASTER,
        OPERATOR,
        MEMBER
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
    private final AdAccountUserId id = new AdAccountUserId();

    @MapsId("adAccountId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adaccount_info_id")
    private AdAccount adAccount;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", length = 10)
    private MemberType memberType;

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
    public AdAccountUser(AdAccount adAccount, User user, MemberType memberType, Status status) {
        this.adAccount = adAccount;
        this.user = user;
        this.memberType = memberType;
        this.status = status;
        this.id.setAdAccountId(adAccount.getId());
        this.id.setUserId(user.getId());
    }

    public void validateStatus() {
        if (!this.status.equals(Status.Y)) throw new AdAccountUserAuthorizationException();
    }

    public void changeStatusY() {
        this.status = AdAccountUser.Status.Y;
    }
    public void changeStatusR() {
        this.status = AdAccountUser.Status.R;
    }
    public void changeStatusC() {
        this.status = AdAccountUser.Status.C;
    }

    public void changeMemberTypeMaster() {
        this.memberType = AdAccountUser.MemberType.MASTER;
    }
    public void changeMemberTypeOperator() {
        this.memberType = AdAccountUser.MemberType.OPERATOR;
    }
    public void changeMemberTypeMember() {
        this.memberType = AdAccountUser.MemberType.MEMBER;
    }
}
