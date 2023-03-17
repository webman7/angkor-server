package com.adplatform.restApi.domain.media.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MediaCategoryId implements Serializable {

    @Column(name = "category_info_id")
    private Integer categoryId;

    @Column(name = "media_info_id")
    private Integer mediaId;
}
