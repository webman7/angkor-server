package com.adplatform.restApi.domain.advertiser.creative.domain;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.media.domain.MediaCategory;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import com.adplatform.restApi.infra.file.service.FileService;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author junny
 * @since 1.0
 */
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "creative_info")
public class Creative extends BaseUpdatedEntity {
    public enum Format {
        IMAGE_BANNER, IMAGE_NATIVE, VIDEO_NATIVE, SERVICE_CONTENT;
    }

    public enum ActionButton {
        DETAIL, BUY, GIFT, ORDER;
    }

    public enum Config {
        ON, OFF, DEL;
    }

    public enum SystemConfig {
        ON, ADMIN_STOP, VOID;
    }

    public enum ReviewStatus {
        /** 승인 */
        APPROVED,
        /** 심사중 */
        WAITING,
        /** 심사 보류 */
        REJECTED,
        /** 수정사항 심사중 */
        MODIFICATION_WAITING,
        /** 수정사항 심사 보류 */
        MODIFICATION_REJECTED;
    }

    public enum Status {
        /** 운영 가능 */
        OPERATING,
        /** 심사 미승인 */
        UNAPPROVED,
        /** 사용자 OFF */
        OFF,
        /** 삭제 */
        DELETED,
        /** 관리자 정지 */
        ADMIN_STOP,
        /** 기간 오류 */
        INVALID_DATE,
        /** 광고그룹 운영불가 */
        ADGROUP_UNAVAILABLE;
    }

    @Column(name = "representative_id")
    private int representativeId;

    @ManyToOne
    @JoinColumn(name = "adgroup_info_id", nullable = false)
    private AdGroup adGroup;

    @OneToMany(mappedBy = "creative", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CreativeFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "creative", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CreativeOpinionProofFile> opinionProofFiles = new ArrayList<>();

    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "format", length = 20)
    private Format format;

    @Column(name = "alt_text", length = 45)
    private String altText;

    @Column(name = "title", length = 25)
    private String title;

    @Column(name = "description", length = 45)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "creative_placement_info",
            joinColumns = @JoinColumn(name = "creative_info_id"),
            inverseJoinColumns = @JoinColumn(name = "placement_info_id"))
    private final Set<Placement> placements = new HashSet<>();

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "creative_media_category_info",
//            joinColumns = @JoinColumn(name = "creative_info_id"),
//            inverseJoinColumns = {
//                    @JoinColumn(name = "category_info_id", referencedColumnName = "category_info_id"),
//                    @JoinColumn(name = "media_info_id", referencedColumnName = "media_info_id")
//            })
//    private final Set<MediaCategory> mediaCategory = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "action_button", length = 20)
    private ActionButton actionButton;

    @Embedded
    private CreativeLanding landing;

    @Column(name = "frequency_cap_type")
    private int frequencyType;

    @Column(name = "frequency_cap")
    private int frequency;

    @Column(name = "opinion", length = 1000)
    private String opinion;

    @Enumerated(EnumType.STRING)
    @Column(name = "config", columnDefinition = "CHAR(5)")
    private Config config;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_config", length = 20)
    private SystemConfig systemConfig;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", length = 50)
    private ReviewStatus reviewStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "creative_status", length = 50)
    private Status status;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @Column(name = "approve_user_no")
    private Integer approveUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "approve_date")
    private LocalDateTime approveAt;

    public Creative(
            int representativeId,
            AdGroup adGroup,
            String name,
            Format format,
            String altText,
            String title,
            String description,
            List<Placement> placements,
            ActionButton actionButton,
            CreativeLanding landing,
            int frequencyType,
            int frequency,
            String opinion,
            Config config,
            SystemConfig systemConfig,
            ReviewStatus reviewStatus,
            Status status) {
        this.representativeId = representativeId;
        this.adGroup = adGroup;
        this.name = name;
        this.format = format;
        this.altText = altText;
        this.title = title;
        this.description = description;
        this.placements.addAll(placements);
        this.actionButton = actionButton;
        this.landing = landing;
        this.frequencyType = frequencyType;
        this.frequency = frequency;
        this.opinion = opinion;
        this.config = config;
        this.systemConfig = systemConfig;
        this.reviewStatus = reviewStatus;
        this.status = status;
    }

    public void addFile(CreativeFile file) {
        this.files.add(file);
    }

    public void addOpinionProofFile(CreativeOpinionProofFile file) {
        this.opinionProofFiles.add(file);
    }

    public Creative deleteOpinionProofFiles(Integer adGroupId, List<String> deleteFilenames, FileService fileService) {
        List<CreativeOpinionProofFile> filesToBeDeleted = this.opinionProofFiles.stream()
                .filter(file -> deleteFilenames.contains(file.getInformation().getOriginalFileName()))
                .collect(Collectors.toList());
        this.opinionProofFiles.removeAll(filesToBeDeleted);
        filesToBeDeleted.forEach(file -> fileService.deleteOpinionProofFiles(adGroupId, file.getInformation().getFilename()));
        return this;
    }

    public Creative update(CreativeDto.Request.Update request, List<Placement> placements) {
        this.representativeId++;
        this.name = request.getName();
        this.title = request.getTitle();
        this.altText = request.getAltText();
        this.description = request.getDescription();
        this.placements.addAll(placements);
        this.actionButton = request.getActionButton();
        this.landing.update(request.getPcLandingUrl(), request.getMobileLandingUrl(), request.getResponsiveLandingUrl());
        this.frequencyType = request.getFrequencyType();
        this.frequency = request.getFrequency();
        this.opinion = request.getOpinion();
        return this;
    }

    public void delete() {
        this.status = Status.DELETED;
        this.config = Config.DEL;
    }

//    public void deletePlacement(Integer creativeId) {
////        this.creativeId = creativeId;
//    }

    public Creative copy(AdGroup adGroup) {
        Creative copy = new Creative();
        copy.adGroup = adGroup;
        copy.files.addAll(this.files.stream().map(c -> c.copy(copy)).collect(Collectors.toList()));
        copy.opinionProofFiles.addAll(this.opinionProofFiles.stream().map(c -> c.copy(copy)).collect(Collectors.toList()));
        copy.name = this.name;
        copy.format = this.format;
        copy.altText = this.altText;
        copy.title = this.title;
        copy.description = this.description;
        copy.placements.addAll(this.placements);
        copy.actionButton = this.actionButton;
        copy.landing = this.landing;
        copy.frequencyType = this.frequencyType;
        copy.opinion = this.opinion;
        copy.config = this.config;
        copy.systemConfig = this.systemConfig;
        copy.reviewStatus = this.reviewStatus;
        copy.status = this.status;
        return copy;
    }

    public void changeConfigOn() {
        this.config = Config.ON;
    }

    public void changeConfigOff() {
        this.config = Config.OFF;
        this.changeStatusOff();
    }

    public void changeStatusOff() {
        this.status = Status.OFF;
    }

    public void changeStatusOperating() {
        this.status = Status.OPERATING;
    }

    public void changeStatusUnApproved() {
        this.status = Status.UNAPPROVED;
    }

    public void changeAdminStopOn() {
        this.systemConfig = SystemConfig.ADMIN_STOP;
        this.config = Config.OFF;
        this.status = Status.ADMIN_STOP;
    }
    public void changeAdminStopOff() { this.systemConfig = SystemConfig.ON; }

    public void changeReviewApprove(CreativeDto.Request.ReviewApprove reviewApprove, Integer loginUserNo) {
        this.reviewStatus = reviewApprove.getReviewStatus();
        if(reviewApprove.getReviewStatus().equals(ReviewStatus.APPROVED)) {
            this.status = Status.OPERATING;
        }
        this.adminMemo = reviewApprove.getAdminMemo();
        this.approveUserNo = loginUserNo;
        this.approveAt = LocalDateTime.now();
    }

    @Getter
    @NoArgsConstructor
    @Entity
    @EntityListeners(AuditingEntityListener.class)
    @Table(name = "creative_placement_info")
    public static class CreativePlacement {
        @EmbeddedId
        private final CreativePlacementId id = new CreativePlacementId();

        @MapsId("creativeId")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "creative_info_id")
        private Creative creative;

        @MapsId("placementId")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "placement_info_id")
        private Placement placement;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Embeddable
    public static class CreativePlacementId implements Serializable {
        @Column(name = "creative_info_id")
        private Integer creativeId;

        @Column(name = "placement_info_id")
        private Integer placementId;
    }
}
