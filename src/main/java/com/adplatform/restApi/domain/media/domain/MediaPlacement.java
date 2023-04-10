package com.adplatform.restApi.domain.media.domain;

import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "media_info_id", nullable = false)
    private Media media;

//    @ManyToOne
//    @JoinColumn(name = "placement_info_id", nullable = false)
//    private Placement placement;

    @Column(name = "placement_info_id")
    private Integer placementId;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width_height_rate", length = 20)
    private String widthHeightRate;

    @Column(name = "url", length = 256)
    private String url;

    @OneToMany(mappedBy = "mediaPlacement", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<MediaPlacementFile> mediaPlacementFiles = new ArrayList<>();

    @Column(name = "memo", length = 2000)
    private String memo;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private MediaPlacement.Status status;

    @Column(name = "approve_user_no")
    private Integer approveUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "approve_date")
    private LocalDateTime approveAt;

    @Builder
    public MediaPlacement(
            Media media,
            Integer placementId,
            String name,
            Integer width,
            Integer height,
            String widthHeightRate,
            String url,
            String memo,
            String adminMemo,
            MediaPlacement.Status status
    ) {
        this.media = media;
        this.placementId = placementId;
        this.name = name;
        this.width = width;
        this.height = height;
        this.widthHeightRate = widthHeightRate;
        this.url = url;
        this.memo = memo;
        this.adminMemo = adminMemo;
        this.status = status;
    }

    public MediaPlacement update(MediaPlacementDto.Request.Update request) {
        if(!request.getPlacementId().equals(0)) {
            this.placementId = request.getPlacementId();
        }
        this.name = request.getName();
        this.width = request.getWidth();
        this.height = request.getHeight();
        this.widthHeightRate = request.getWidthHeightRate();
        this.url = request.getUrl();
        this.memo = request.getMemo();
        return this;
    }

    public MediaPlacement updateApproveAfter(MediaPlacementDto.Request.Update request) {
        this.name = request.getName();
        return this;
    }

    public MediaPlacement updateAdminMemo(MediaPlacementDto.Request.Update request) {
        this.adminMemo = request.getAdminMemo();
        return this;
    }

    public MediaPlacement updateAdminApproveNew(MediaPlacementDto.Request.Update request, Integer placementId) {
        this.placementId = placementId;
        this.approveUserNo = SecurityUtils.getLoginUserNo();
        this.approveAt = LocalDateTime.now();
        this.adminMemo = request.getAdminMemo();
        this.status = MediaPlacement.Status.Y;
        return this;
    }

    public MediaPlacement updateAdminApprove(MediaPlacementDto.Request.Update request) {
        this.approveUserNo = SecurityUtils.getLoginUserNo();
        this.approveAt = LocalDateTime.now();
        this.adminMemo = request.getAdminMemo();
        this.status = MediaPlacement.Status.Y;
        return this;
    }

    public void changeStatusD() {
        this.status = MediaPlacement.Status.D;
    }
    public void changeStatusR() {
        this.status = MediaPlacement.Status.R;
    }
    public void changeStatusN() {
        this.status = MediaPlacement.Status.N;
    }
    public void delete() {
        this.status = MediaPlacement.Status.D;
    }

    public void addMediaPlacementFile(MediaPlacementFile file) {
        this.mediaPlacementFiles.add(file);
    }
}
