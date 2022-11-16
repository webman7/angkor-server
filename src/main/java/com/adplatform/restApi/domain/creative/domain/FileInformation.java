package com.adplatform.restApi.domain.creative.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FileInformation {
    public enum FileType {
        IMAGE, VIDEO
    }

    @Column(name = "url", length = 1000)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", length = 20)
    private FileType fileType;

    @Column(name = "file_size", columnDefinition = "INT")
    private long fileSize;

    @Column(name = "file_name", length = 200)
    private String filename;

    @Column(name = "original_file_name", length = 200)
    private String originalFileName;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Builder
    public FileInformation(
            String url,
            FileType fileType,
            long fileSize,
            String filename,
            String originalFileName,
            int width,
            int height,
            String mimeType) {
        this.url = url;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.filename = filename;
        this.originalFileName = originalFileName;
        this.width = width;
        this.height = height;
        this.mimeType = mimeType;
    }
}
