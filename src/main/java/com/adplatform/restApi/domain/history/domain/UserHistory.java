package com.adplatform.restApi.domain.history.domain;

import com.adplatform.restApi.domain.user.domain.Password;
import com.adplatform.restApi.domain.user.domain.User;
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
@Table(name = "user_info_history")
public class UserHistory extends HistoryCreatedEntity {

    @Column(name = "user_no", nullable = false)
    private Integer userNo;
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "active", columnDefinition = "CHAR(1)")
    private User.Active active;

    @Column(name = "pwd_wrong_cnt")
    private Integer pwdWrongCnt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "pwd_upd_date")
    private LocalDateTime pwdUpdatedAt;

    @Column(name = "status_chg_user_no")
    private Integer statusChangedUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "status_chg_date")
    private LocalDateTime statusChangedAt;

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
    public UserHistory(Integer userNo, String userId, String name, String phone, User.Active active,
                       Integer pwdWrongCnt,
                       LocalDateTime pwdUpdatedAt,
                       Integer statusChangedUserNo,
                       LocalDateTime statusChangedAt,
                       Integer regUserNo,
                       LocalDateTime createdAt,
                       Integer updUserNo,
                       LocalDateTime updatedAt
                       ) {
        this.userNo = userNo;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.active = active;
        this.pwdWrongCnt = pwdWrongCnt;
        this.pwdUpdatedAt = pwdUpdatedAt;
        this.statusChangedUserNo = statusChangedUserNo;
        this.statusChangedAt = statusChangedAt;
        this.regUserNo = regUserNo;
        this.createdAt = createdAt;
        this.updUserNo = updUserNo;
        this.updatedAt = updatedAt;
    }
}
