package com.adplatform.restApi.domain.media.domain;

import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "approve_user_no")
    private Integer approveUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "approve_date")
    private LocalDateTime approveAt;

    @Builder
    public Placement(
            String name,
            Integer width,
            Integer height,
            String widthHeightRate,
            String memo,
            String adminMemo,
            Status status
    ) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.widthHeightRate = widthHeightRate;
        this.memo = memo;
        this.adminMemo = adminMemo;
        this.status = status;
    }

    public Placement update(PlacementDto.Request.Update request) {
        this.name = request.getName();
        this.memo = request.getMemo();
        this.adminMemo = request.getAdminMemo();
        return this;
    }

    public void delete() {
        this.status = Status.D;
    }
}
