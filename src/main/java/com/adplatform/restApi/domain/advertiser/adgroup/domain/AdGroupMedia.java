package com.adplatform.restApi.domain.advertiser.adgroup.domain;

import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.domain.Media;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "adgroup_media_info")
public class AdGroupMedia {
    @EmbeddedId
    private final  AdGroupMediaId id = new AdGroupMediaId();

    @MapsId("adGroupId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adgroup_info_id")
    private AdGroup adGroup;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_info_id")
    private Category category;

    @MapsId("mediaId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_info_id")
    private Media media;
}
