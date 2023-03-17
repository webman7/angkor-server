package com.adplatform.restApi.domain.media.domain;

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
@Table(name = "media_placement_info")
public class MediaPlacement extends BaseUpdatedEntity {

    public enum Status {
        /** 요청중 */
        N,
        /** 승인 */
        Y,
        /** 거절 */
        R,
        /** 삭제 */
        D
    }

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "media_info_id", nullable = false)
    private Media media;

    @ManyToOne
    @JoinColumn(name = "placement_info_id", nullable = false)
    private Placement placement;

    @Column(name = "memo", length = 2000)
    private String memo;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private Placement.Status status;
}
