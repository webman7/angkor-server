package com.adplatform.restApi.domain.advertiser.creative.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CreativePlacementId implements Serializable {
    private static final long serialVersionUID = -2846137600408817356L;

    @Column(name = "creative_info_id")
    private Integer creativeId;
    @Column(name = "placement_info_id")
    private Integer placementId;
}
