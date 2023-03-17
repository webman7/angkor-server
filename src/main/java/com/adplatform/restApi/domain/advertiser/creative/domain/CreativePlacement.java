package com.adplatform.restApi.domain.advertiser.creative.domain;

import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.Placement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "creative_placement_info")
public class CreativePlacement {
    @EmbeddedId
    private final CreativePlacementId id = new CreativePlacementId();

    @MapsId("creativeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creative_info_id")
    private Creative creative;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_info_id")
    private Category category;

    @MapsId("mediaId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_info_id")
    private Media media;

    @MapsId("placementId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placement_info_id")
    private Placement placement;
}
