package com.adplatform.restApi.domain.company.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyFileDto {
    private String type;
    private long fileSize;
    private String filename;
    private String originalFileName;
    private String url;
    private String mimeType;
}
