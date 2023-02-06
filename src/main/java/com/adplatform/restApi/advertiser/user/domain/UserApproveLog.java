package com.adplatform.restApi.advertiser.user.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_approve_log")
public class UserApproveLog extends BaseCreatedEntity {
    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "prev_active", nullable = false, columnDefinition = "CHAR(1)")
    private String prevActive;
}
