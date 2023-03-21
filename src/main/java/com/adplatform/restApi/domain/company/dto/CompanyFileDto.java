package com.adplatform.restApi.domain.company.dto;


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
                    long fileSize,
                    String filename,
                    String originalFileName,
                    String url,
                    String mimeType
            ) {
                this.id = id;
                this.companyId = companyId;
                this.type = type;
                this.fileSize = fileSize;
                this.filename = filename;
                this.originalFileName = originalFileName;
                this.url = url;
                this.mimeType = mimeType;
            }
        }
    }

}
