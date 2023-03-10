package com.adplatform.restApi.domain.history.domain;

import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "user_password_change_history")
public class UserPasswordChangeHistory extends BaseCreatedEntity {

    public enum Status {
        READY, WAITING, FINISHED
    }
    @Column(name = "user_id")
    private String userId;

    @Column(name = "cert_no")
    private String certNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private Status status;
    @Column(name = "reg_ip", length = 30)
    private String regIp;
    @Column(name = "cert_ip", length = 30)
    private String certIp;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "cert_date")
    private LocalDateTime certAt;

    @Builder
    public UserPasswordChangeHistory(
            String userId,
            String certNo,
            Status status,
            String regIp) {
        this.userId = userId;
        this.certNo = certNo;
        this.status = status;
        this.regIp = regIp;
    }

    public UserPasswordChangeHistory updateStatus(Status status) {
        this.status = status;
        return this;
    }

    public UserPasswordChangeHistory updateFinished(Status status, String certIp) {
        this.status = status;
        this.certIp = certIp;
        this.certAt = LocalDateTime.now();
        return this;
    }
}
