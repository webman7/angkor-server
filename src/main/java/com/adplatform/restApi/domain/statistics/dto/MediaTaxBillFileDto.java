package com.adplatform.restApi.domain.statistics.dto;

import com.adplatform.restApi.domain.statistics.domain.taxbill.FileInformation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class MediaTaxBillFileDto {
    private FileInformation.FileType fileType;
    private long fileSize;
    private String filename;
    private String originalFileName;
    private String url;
    private int width;
    private int height;
    private String mimeType;

    public static abstract class Response {
        @Getter
        @Setter
        public static class FileInfo {
            private FileInformation.FileType fileType;
            private long fileSize;
            private String filename;
            private String originalFileName;
            private String url;
            private String mimeType;

            @QueryProjection
            public FileInfo(
                    FileInformation.FileType fileType,
                    long fileSize,
                    String filename,
                    String originalFileName,
                    String url,
                    String mimeType
            ) {
                this.fileType = fileType;
                this.fileSize = fileSize;
                this.filename = filename;
                this.originalFileName = originalFileName;
                this.url = url;
                this.mimeType = mimeType;
            }
        }
    }
}
