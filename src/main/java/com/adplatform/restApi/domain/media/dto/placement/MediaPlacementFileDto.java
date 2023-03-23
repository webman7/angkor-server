package com.adplatform.restApi.domain.media.dto.placement;

import com.adplatform.restApi.domain.media.domain.FileInformation;

public class MediaPlacementFileDto {
    private FileInformation.FileType fileType;
    private long fileSize;
    private String filename;
    private String originalFileName;
    private String url;
    private int width;
    private int height;
    private String mimeType;
}
