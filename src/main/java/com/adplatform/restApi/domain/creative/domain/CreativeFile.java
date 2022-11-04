package com.adplatform.restApi.domain.creative.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "creative_files")
public class CreativeFile extends BaseEntity {
    public enum Type {
        PROFILE, PROMOTIONAL, CATALOG
    }

    public enum FileType {
        IMAGE, VIDEO
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creative_info_id")
    private Creative creative;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private Type type;

    @Column(name = "url", length = 1000)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", length = 20)
    private FileType fileType;

    @Column(name = "file_size")
    private int fileSize;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "original_file_name", length = 200)
    private String originalFileName;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "mime_type", length = 100)
    private String mimeType;
}
