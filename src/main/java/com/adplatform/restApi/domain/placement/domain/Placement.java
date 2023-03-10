package com.adplatform.restApi.domain.placement.domain;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.Media;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "placement_info")
public class Placement extends BaseUpdatedEntity {

    public enum Status {
        /** 승인 */
        Y,
        /** 미승인 */
        N,
        /** 삭제 */
        D
    }

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "media_info_id", nullable = false)
    private Media media;

    private Integer width;

    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private Status status;
}
