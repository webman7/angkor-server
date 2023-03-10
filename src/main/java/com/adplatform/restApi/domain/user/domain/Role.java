package com.adplatform.restApi.domain.user.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "roles_info")
public class Role extends BaseEntity {
    public enum Type {
        ROLE_ADMIN,
        ROLE_OPERATOR,
        ROLE_COMPANY_ADMINISTRATOR,
        ROLE_COMPANY_ACCOUNTANT,
        ROLE_COMPANY_GENERAL
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 50, insertable = false, updatable = false)
    private Type value;
}
