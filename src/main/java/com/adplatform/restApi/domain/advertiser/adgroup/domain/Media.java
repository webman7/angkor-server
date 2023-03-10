package com.adplatform.restApi.domain.advertiser.adgroup.domain;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
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
@Table(name = "media_info")
public class Media extends BaseUpdatedEntity {

    public enum Status {
        /** 승인 */
        Y,
        /** 요청 */
        N,
        /** 거부 */
        R,
        /** 삭제 */
        D
    }
    
    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "app_key", length = 30)
    private String appKey;

    @Column(name = "app_secret", length = 128)
    private String appSecret;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private Status status;
}
