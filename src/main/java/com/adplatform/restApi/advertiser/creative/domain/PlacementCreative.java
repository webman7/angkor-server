package com.adplatform.restApi.advertiser.creative.domain;

import com.adplatform.restApi.placement.domain.Placement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "placement_creative_info")
public class PlacementCreative {
    @EmbeddedId
    private final PlacementCreativeId id = new PlacementCreativeId();

    @MapsId("placementId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placement_info_id")
    private Placement placement;

    @MapsId("creativeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creative_info_id")
    private Creative creative;
}
