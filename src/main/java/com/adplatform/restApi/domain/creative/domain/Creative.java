package com.adplatform.restApi.domain.creative.domain;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seohyun Lee
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
        ON, ADMIN_STOP, EXTERNAL_SERVICE_STOP;
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
        /** 기간 오류 */
        INVALID_DATE,
        /** 관리자 정지 */
        MONITORING_REJECTED,
        /** 사용자 OFF */
        OFF,
        /** 삭제 */
        DELETED,
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

    @Enumerated(EnumType.STRING)
    @Column(name = "action_button", length = 20)
    private ActionButton actionButton;

    @Embedded
    private CreativeLanding landing;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "frequency_cap_type_yn", columnDefinition = "CHAR")
    private boolean frequencyType;

    @Column(name = "frequency_cap")
    private int frequency;

    @Column(name = "opinion", length = 1000)
    private String opinion;

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

    public Creative(
            int representativeId,
            AdGroup adGroup,
            String name,
            Format format,
            String altText,
            String title,
            String description,
            ActionButton actionButton,
            CreativeLanding landing,
            boolean frequencyType,
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

    public Creative clearOpinionProofFile() {
        this.opinionProofFiles.clear();
        return this;
    }

    public Creative update(CreativeDto.Request.Update request) {
        this.name = request.getName();
        this.title = request.getTitle();
        this.altText = request.getAltText();
        this.description = request.getDescription();
        this.actionButton = request.getActionButton();
        this.landing.update(request.getPcLandingUrl(), request.getMobileLandingUrl(), request.getResponsiveLandingUrl());
        this.frequencyType = request.isFrequencyType();
        this.frequency = request.getFrequency();
        this.opinion = request.getOpinion();
        return this;
    }
}
