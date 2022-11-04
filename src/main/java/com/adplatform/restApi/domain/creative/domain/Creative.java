package com.adplatform.restApi.domain.creative.domain;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "creative_info")
public class Creative extends BaseUpdatedEntity {
    public enum Format {
        IMAGE_BANNER, IMAGE_NATIVE, SERVICE_CONTENT
    }

    public enum ActionButton {
        DETAIL, BUY, GIFT, ORDER
    }

    public enum LandingType {
        URL
    }

    public enum Config {
        ON, OFF, DEL
    }

    public enum SystemConfig {
        ON, ADMIN_STOP, EXTERNAL_SERVICE_STOP
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
        MODIFICATION_REJECTED
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
        ADGROUP_UNAVAILABLE
    }

    @Column(name = "representative_id")
    private int representativeId;

    @ManyToOne
    @JoinColumn(name = "adgroup_info_id", nullable = false)
    private AdGroup adGroup;

    @OneToMany(mappedBy = "creative", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreativeFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "creative", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreativeOptionProof> creativeOptionProofs = new ArrayList<>();

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

    @Enumerated(EnumType.STRING)
    @Column(name = "landing_type", length = 20)
    private LandingType landingType;

    @Column(name = "pc_landing_url", length = 1000)
    private String pcLandingUrl;

    @Column(name = "mobile_landing_url", length = 1000)
    private String mobileLandingUrl;

    @Column(name = "rspv_landing_url", length = 1000)
    private String responsiveLandingUrl;

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
}
