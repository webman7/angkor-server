package com.adplatform.restApi.domain.history.domain;

import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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
    @Column(name = "user_name")
    private String userName;

    @Column(name = "cert_no")
    private String certNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private Status status;
    @Column(name = "reg_ip", length = 30)
    private String regIp;

    @Builder
    public UserPasswordChangeHistory(
            String userId,
            String userName,
            String certNo,
            Status status,
            String regIp) {
        this.userId = userId;
        this.userName = userName;
        this.certNo = certNo;
        this.status = status;
        this.regIp = regIp;
    }

    public UserPasswordChangeHistory updateStatus(Status status) {
        this.status = status;
        return this;
    }
}
