package com.adplatform.restApi.domain.creative.dto;

import com.adplatform.restApi.domain.creative.domain.FileInformation;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
public class CreativeOpinionProofFileDto {
    private FileInformation.FileType fileType;
    private long fileSize;
    private String filename;
    private String originalFileName;
    private String url;
    private int width;
    private int height;
    private String mimeType;
}
