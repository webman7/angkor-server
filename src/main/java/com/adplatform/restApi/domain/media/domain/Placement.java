package com.adplatform.restApi.domain.media.domain;

import com.adplatform.restApi.domain.media.domain.Media;
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

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width_height_rate", length = 20)
    private String widthHeightRate;

    @Column(name = "memo", length = 2000)
    private String memo;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private Status status;
}
