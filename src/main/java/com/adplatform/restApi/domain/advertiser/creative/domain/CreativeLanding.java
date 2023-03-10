package com.adplatform.restApi.domain.advertiser.creative.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CreativeLanding {
    public enum LandingType {
        URL
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "landing_type", length = 20)
    private LandingType landingType;

    @Column(name = "pc_landing_url", length = 1000)
    private String pcLandingUrl;

    @Column(name = "mobile_landing_url", length = 1000)
    private String mobileLandingUrl;

    @Column(name = "rspv_landing_url", length = 1000)
    private String responsiveLandingUrl;

    public CreativeLanding(
            LandingType landingType,
            String pcLandingUrl,
            String mobileLandingUrl,
            String responsiveLandingUrl) {
        this.landingType = landingType;
        this.pcLandingUrl = pcLandingUrl;
        this.mobileLandingUrl = mobileLandingUrl;
        this.responsiveLandingUrl = responsiveLandingUrl;
    }

    public void update(String pcLandingUrl, String mobileLandingUrl, String responsiveLandingUrl) {
        this.pcLandingUrl = pcLandingUrl;
        this.mobileLandingUrl = mobileLandingUrl;
        this.responsiveLandingUrl = responsiveLandingUrl;
    }
}
