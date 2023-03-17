package com.adplatform.restApi.domain.advertiser.adgroup.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AdGroupMediaId implements Serializable {
    @Column(name = "adgroup_info_id")
    private Integer adGroupId;

    @Column(name = "category_info_id")
    private Integer categoryId;

    @Column(name = "media_info_id")
    private Integer mediaId;
}
