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
@Table(name = "user_login_history")
public class UserLoginHistory extends BaseCreatedEntity {

    @Column(name = "login_type")
    private Integer loginType;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reg_ip", length = 30)
    private String regIp;

    @Builder
    public UserLoginHistory(
            Integer loginType,
            String userId,
            String regIp) {
        this.loginType = loginType;
        this.userId = userId;
        this.regIp = regIp;
    }
}
