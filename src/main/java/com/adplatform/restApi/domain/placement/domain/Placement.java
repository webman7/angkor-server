package com.adplatform.restApi.domain.placement.domain;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.Media;
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

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "media_info_id", nullable = false)
    private Media media;

    private Integer width;

    private Integer height;

    /**
     * 지면 삭제 여부<br/>
     * {@link Boolean#TRUE true}: 지면 삭제됨.<br>
     * {@link Boolean#FALSE false}: 지면 설정됨.
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "del_yn", columnDefinition = "CHAR")
    private boolean deleted;
}
