package com.adplatform.restApi.domain.placement.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PlacementCreativeId implements Serializable {
    @Column(name = "placement_info_id")
    private Integer placementId;

    @Column(name = "creative_info_id")
    private Integer creativeId;
}
