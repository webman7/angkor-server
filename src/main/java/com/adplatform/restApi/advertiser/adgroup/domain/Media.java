package com.adplatform.restApi.advertiser.adgroup.domain;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_info")
public class Media extends BaseUpdatedEntity {
    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "app_key", length = 30)
    private String appKey;

    @Column(name = "app_secret", length = 128)
    private String appSecret;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "del_yn", columnDefinition = "CHAR")
    private boolean deleted;
}
