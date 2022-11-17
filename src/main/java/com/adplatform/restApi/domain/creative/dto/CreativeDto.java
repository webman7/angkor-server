package com.adplatform.restApi.domain.creative.dto;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupIdGetter;
import com.adplatform.restApi.domain.creative.domain.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
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
            private Integer campaignId;
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
                    Integer campaignId,
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
                this.campaignId = campaignId;
                this.adGroupId = adGroupId;
                this.adGroupName = adGroupName;
                this.fileId = fileId;
                this.fileName = fileName;
            }
        }

        @Getter
        @Setter
        public static class Detail {
            private List<CreativeFileDto> files;
            List<CreativeOpinionProofFileDto> opinionProofFiles;
            private String name;
            private Creative.Format format;
            private String altText;
            private String title;
            private String description;
            private Creative.ActionButton actionButton;
            private CreativeLanding landing;
            private boolean frequencyType;
            private int frequency;
            private String opinion;

            @QueryProjection
            public Detail(
                    List<CreativeFileDto> files,
                    List<CreativeOpinionProofFileDto> opinionProofFiles,
                    String name,
                    Creative.Format format,
                    String altText,
                    String title,
                    String description,
                    Creative.ActionButton actionButton,
                    CreativeLanding landing,
                    boolean frequencyType,
                    int frequency,
                    String opinion) {
                this.files = files;
                this.opinionProofFiles = opinionProofFiles;
                this.name = name;
                this.format = format;
                this.altText = altText;
                this.title = title;
                this.description = description;
                this.actionButton = actionButton;
                this.landing = landing;
                this.frequencyType = frequencyType;
                this.frequency = frequency;
                this.opinion = opinion;
            }
        }
    }
}
