package com.adplatform.restApi.domain.creative.dto;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupIdGetter;
import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.creative.domain.CreativeLanding;
import com.adplatform.restApi.domain.creative.domain.FileInformation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public abstract class CreativeDto {
    public abstract static class Request {
        @Getter
        @Setter
        public static class Save implements AdGroupIdGetter {
            @NotNull
            private Integer adGroupId;
            @NotNull
            private FileInformation.FileType fileType;
            @NotNull
            private CreativeFile.Type type;
            @NotNull
            @Size(min = 1, max = 100)
            private List<MultipartFile> files = new ArrayList<>();
            private List<MultipartFile> opinionProofFiles = new ArrayList<>();
            @NotEmpty
            private String name;
            @NotNull
            private Creative.Format format;
            private String altText;
            private String title;
            private String description;
            @NotNull
            private Creative.ActionButton actionButton;
            @NotNull
            private CreativeLanding.LandingType landingType;
            private String pcLandingUrl;
            private String mobileLandingUrl;
            private String responsiveLandingUrl;
            private boolean frequencyType;
            private int frequency;
            private String opinion;
        }

        @Getter
        @Setter
        public static class Search implements AdAccountIdGetter {
            @NotNull
            private Integer adAccountId;
            private String name;
        }
    }

    public abstract static class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;
            private String name;
            private Creative.Config config;
            private Creative.SystemConfig systemConfig;
            private Creative.ReviewStatus reviewStatus;
            private Creative.Status status;
            private Integer adGroupId;
            private String adGroupName;
            private Integer fileId;
            private String fileName;

            @QueryProjection
            public Default(
                    Integer id,
                    String name,
                    Creative.Config config,
                    Creative.SystemConfig systemConfig,
                    Creative.ReviewStatus reviewStatus,
                    Creative.Status status,
                    Integer adGroupId,
                    String adGroupName,
                    Integer fileId,
                    String fileName) {
                this.id = id;
                this.name = name;
                this.config = config;
                this.systemConfig = systemConfig;
                this.reviewStatus = reviewStatus;
                this.status = status;
                this.adGroupId = adGroupId;
                this.adGroupName = adGroupName;
                this.fileId = fileId;
                this.fileName = fileName;
            }
        }
    }
}
