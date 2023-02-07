package com.adplatform.restApi.domain.history.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "user_password_change_history")
public class UserPasswordChangeHistory extends BaseCreatedEntity {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "reg_ip", length = 30)
    private String regIp;

    @Builder
    public UserPasswordChangeHistory(
            String userId,
            String userName,
            String regIp) {
        this.userId = userId;
        this.userName = userName;
        this.regIp = regIp;
    }
}
