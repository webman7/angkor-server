package com.adplatform.restApi.domain.creative.dto;

import com.adplatform.restApi.domain.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.creative.domain.FileInformation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreativeFileDto {
    private CreativeFile.Type type;
    private FileInformation.FileType fileType;
    private long fileSize;
    private String filename;
    private String originalFileName;
    private int width;
    private int height;
    private String mimeType;
}
