package com.adplatform.restApi.domain.placement.domain;

import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.creative.domain.FileInformation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
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
