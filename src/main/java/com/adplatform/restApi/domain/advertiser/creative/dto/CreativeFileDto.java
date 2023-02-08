package com.adplatform.restApi.domain.advertiser.creative.dto;

import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.advertiser.creative.domain.FileInformation;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
public class CreativeFileDto {
    private CreativeFile.Type type;
    private FileInformation.FileType fileType;
    private long fileSize;
    private String filename;
    private String originalFileName;
    private String url;
    private int width;
    private int height;
    private String mimeType;
}