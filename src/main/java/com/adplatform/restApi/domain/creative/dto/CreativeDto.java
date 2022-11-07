package com.adplatform.restApi.domain.creative.dto;

import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.creative.domain.CreativeLanding;
import com.adplatform.restApi.domain.creative.domain.FileInformation;
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
        public static class Save {
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
    }
}
