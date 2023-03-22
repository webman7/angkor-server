package com.adplatform.restApi.domain.company.dto;


import com.adplatform.restApi.domain.company.domain.FileInformation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyFileDto {

    public static abstract class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;
            private Integer companyId;
            private String type;
            private FileInformation.FileType fileType;
            private long fileSize;
            private String filename;
            private String originalFileName;
            private String url;
            private String mimeType;

            @QueryProjection
            public Default(
                    Integer id,
                    Integer companyId,
                    String type,
                    FileInformation.FileType fileType,
                    long fileSize,
                    String filename,
                    String originalFileName,
                    String url,
                    String mimeType
            ) {
                this.id = id;
                this.companyId = companyId;
                this.type = type;
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
