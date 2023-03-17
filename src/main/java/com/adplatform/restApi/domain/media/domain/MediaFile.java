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
@Table(name = "media_files")
public class MediaFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_info_id")
    private Media media;

    @Embedded
    private FileInformation information;

    public MediaFile(Media media, FileInformation information) {
        this.media = media;
        this.information = information;
    }

    public MediaFile copy(Media media) {
        MediaFile copy = new MediaFile();
        copy.media = media;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}