package com.adplatform.restApi.domain.advertiser.campaign.domain;

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
@Table(name = "ad_type_info")
public class AdType extends BaseUpdatedEntity {
    @Column(name = "name", length = 20)
    private String name;

    /**
     * 광고 목표 삭제 여부<br/>
     * {@link Boolean#TRUE true}: 광고 타입 삭제됨.<br>
     * {@link Boolean#FALSE false}: 광고 타입 존재함.
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "del_yn", columnDefinition = "CHAR")
    private boolean deleted;
}
