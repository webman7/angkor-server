package com.adplatform.restApi.advertiser.adaccount.domain;

import com.adplatform.restApi.advertiser.adaccount.exception.AdAccountUserAuthorizationException;
import com.adplatform.restApi.advertiser.user.domain.User;
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
@Table(name = "adaccount_user_info")
public class AdAccountUser {
    public enum MemberType {
        MASTER,
        OPERATOR,
        MEMBER
    }

    public enum RequestStatus {
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
    @Column(name = "request_status", columnDefinition = "CHAR")
    private RequestStatus requestStatus;

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
    public AdAccountUser(AdAccount adAccount, User user, MemberType memberType, RequestStatus requestStatus) {
        this.adAccount = adAccount;
        this.user = user;
        this.memberType = memberType;
        this.requestStatus = requestStatus;
        this.id.setAdAccountId(adAccount.getId());
        this.id.setUserId(user.getId());
    }

    public void validateRequestStatus() {
        if (!this.requestStatus.equals(RequestStatus.Y)) throw new AdAccountUserAuthorizationException();
    }
}
