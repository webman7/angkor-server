package com.adplatform.restApi.domain.media.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_placement_files")
public class MediaPlacementFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_placement_info_id")
    private MediaPlacement mediaPlacement;

    @Embedded
    private FileInformation information;

    public MediaPlacementFile(MediaPlacement mediaPlacement, FileInformation information) {
        this.mediaPlacement = mediaPlacement;
        this.information = information;
    }

    public MediaPlacementFile copy(MediaPlacement mediaPlacement) {
        MediaPlacementFile copy = new MediaPlacementFile();
        copy.mediaPlacement = mediaPlacement;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}