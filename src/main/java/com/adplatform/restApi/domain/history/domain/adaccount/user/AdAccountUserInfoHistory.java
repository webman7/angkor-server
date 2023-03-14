package com.adplatform.restApi.domain.history.domain.adaccount.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
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
@Table(name = "adaccount_user_info_history")
public class AdAccountUserInfoHistory extends HistoryCreatedEntity {
    @Column(name = "adaccount_info_id", nullable = false)
    private Integer adAccountId;

    @Column(name = "user_no")
    private Integer userNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", length = 10)
    private AdAccountUser.MemberType memberType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private AdAccountUser.Status status;

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
    public AdAccountUserInfoHistory(
            Integer adAccountId,
            Integer userNo,
            AdAccountUser.MemberType memberType,
            AdAccountUser.Status status,
            Integer regUserNo,
            LocalDateTime createdAt,
            Integer updUserNo,
            LocalDateTime updatedAt) {
        this.adAccountId = adAccountId;
        this.userNo = userNo;
        this.memberType = memberType;
        this.status = status;
        this.regUserNo = regUserNo;
        this.createdAt = createdAt;
        this.updUserNo = updUserNo;
        this.updatedAt = updatedAt;
    }
}
