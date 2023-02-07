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
@Table(name = "user_roles_change_history")
public class UserRolesChangeHistory extends BaseCreatedEntity {

    @Column(name = "prev_roles", length = 100)
    private String prevRoles;

    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "roles", length = 100)
    private String roles;

    @Builder
    public UserRolesChangeHistory(
            String prevRoles,
            Integer userNo,
            String roles) {
        this.prevRoles = prevRoles;
        this.userNo = userNo;
        this.roles = roles;
    }
}
