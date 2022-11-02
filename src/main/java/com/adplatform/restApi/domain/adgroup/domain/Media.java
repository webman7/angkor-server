package com.adplatform.restApi.domain.adgroup.domain;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_info")
public class Media extends BaseUpdatedEntity {
    @Column(name = "name", length = 20)
    private String name;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "del_yn", columnDefinition = "CHAR")
    private boolean deleted;
}
