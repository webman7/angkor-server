package com.adplatform.restApi.domain.advertiser.creative.dto;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupIdGetter;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeLanding;
import com.adplatform.restApi.domain.advertiser.creative.domain.FileInformation;
import com.adplatform.restApi.domain.statistics.dto.ReportDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author junny
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
            @NotBlank
            private String name;
            @NotNull
            private Creative.Format format;
            private String altText;
            @NotBlank
            private String title;
            private String description;
            @NotNull
            private Creative.ActionButton actionButton;
            @NotNull
            private CreativeLanding.LandingType landingType;
            private String pcLandingUrl;
            private String mobileLandingUrl;
            private String responsiveLandingUrl;
            private int frequencyType;
            private int frequency;
            private List<Integer> placements;
            private String opinion;
        }

        @Getter
        @Setter
        public static class Search implements AdAccountIdGetter {
            @NotNull
            private Integer adAccountId;
            private String name;
        }

        @Getter
        @Setter
        public static class Update implements CreativeIdGetter {
            @NotNull
            private Integer creativeId;
            private Integer adGroupId;
            private List<MultipartFile> opinionProofFiles = new ArrayList<>();
            private List<String> deleteOpinionProofFilenames = new ArrayList<>();
            @NotBlank
            private String name;
            @NotBlank
            private String title;
            private String altText;
            private String description;
            @NotNull
            private Creative.ActionButton actionButton;
            private String pcLandingUrl;
            private String mobileLandingUrl;
            private String responsiveLandingUrl;
            private int frequencyType;
            private int frequency;
            private List<Integer> placements;
            private String opinion;
        }
    }

    public abstract static class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;
            private String name;
            private String config;
            private String systemConfig;
            private String reviewStatus;
            private String status;
            private Integer campaignId;
            private String campaignName;
            private Integer adGroupId;
            private String adGroupName;
            private Integer fileId;
            private String fileName;
            private String url;
            private FileInformation.FileType fileType;
            private ReportDto.Response report;
        }

        @Getter
        @Setter
        public static class Detail {
            private List<CreativeFileDto> files;
            List<CreativeOpinionProofFileDto> opinionProofFiles;
            private String name;
            private CampaignDto.Response.BaseDetail campaign;
            private String adGroupName;
            private Creative.Format format;
            private String altText;
            private String title;
            private String description;
            private Creative.ActionButton actionButton;
            private CreativeLanding landing;
            private int frequencyType;
            private int frequency;
            private List<Integer> placements;
            private String opinion;

            @QueryProjection
            public Detail(
                    List<CreativeFileDto> files,
                    List<CreativeOpinionProofFileDto> opinionProofFiles,
                    String name,
                    String adGroupName,
                    CampaignDto.Response.BaseDetail campaign,
                    Creative.Format format,
                    String altText,
                    String title,
                    String description,
                    Creative.ActionButton actionButton,
                    CreativeLanding landing,
                    int frequencyType,
                    int frequency,
                    List<Integer> placements,
                    String opinion) {
                this.files = files;
                this.opinionProofFiles = opinionProofFiles;
                this.name = name;
                this.adGroupName = adGroupName;
                this.campaign = campaign;
                this.format = format;
                this.altText = altText;
                this.title = title;
                this.description = description;
                this.actionButton = actionButton;
                this.landing = landing;
                this.frequencyType = frequencyType;
                this.frequency = frequency;
                this.placements = placements;
                this.opinion = opinion;
            }
        }
    }
}
