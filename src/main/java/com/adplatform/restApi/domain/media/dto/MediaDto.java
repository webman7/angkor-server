package com.adplatform.restApi.domain.media.dto;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.media.domain.*;
import com.adplatform.restApi.domain.media.dto.category.MediaCategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MediaDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            @Size(min = 1, max = 50)
            @NotBlank
            private String name;
            private Integer companyId;
            private List<Integer> category;
            private String url;
            private List<MultipartFile> mediaFiles = new ArrayList<>();
            private Integer expInventory;
            private String memo;
            private String adminMemo;
        }

        @Getter
        @Setter
        public static class Update extends MediaDto.Request.Save {
            @NotNull
            private Integer id;
        }

        @Getter
        @Setter
        public static class Confirm {
            private Integer id;
            private List<Integer> category;
            private String adminMemo;
        }

        @Getter
        @Setter
        public static class Search {
            private Integer companyId;
            private String status;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;
            private String name;
//            private String appKey;
//            private String appSecret;
            @QueryProjection
            public Default(
                    Integer id,
                    String name) {
                this.id = id;
                this.name = name;
            }
        }

        @Getter
        @Setter
        public static class Search {
            @NotNull
            private Integer id;
            private String name;
            private CompanyDto.Response.Default company;
            private String appKey;
            private String appSecret;
            private String url;
            private String mediaFileUrl;
            private String mediaFileName;
            private FileInformation.FileType fileType;
            private Integer expInventory;
            private String memo;
            private String adminMemo;
            private Media.Status status;
            private String regUserId;
            private LocalDateTime createdAt;

            @QueryProjection
            public Search(
                    Integer id,
                    String name,
                    CompanyDto.Response.Default company,
                    String appKey,
                    String appSecret,
                    String url,
                    String mediaFileUrl,
                    String mediaFileName,
                    FileInformation.FileType fileType,
                    Integer expInventory,
                    String memo,
                    String adminMemo,
                    Media.Status status,
                    String regUserId,
                    LocalDateTime createdAt) {
                this.id = id;
                this.name = name;
                this.company = company;
                this.appKey = appKey;
                this.appSecret = appSecret;
                this.url = url;
                this.mediaFileUrl = mediaFileUrl;
                this.mediaFileName = mediaFileName;
                this.fileType = fileType;
                this.expInventory = expInventory;
                this.memo = memo;
                this.adminMemo = adminMemo;
                this.status = status;
                this.regUserId = regUserId;
                this.createdAt = createdAt;
            }
        }

        @Getter
        @Setter
        public static class MediaInfo {
            @NotNull
            private Integer id;
            private String name;
            private CompanyDto.Response.Default company;
            private String appKey;
            private String appSecret;
            private String url;
            private List<Category> category;
            private MediaFileDto.Response.FileInfo mediaFiles;
            private Integer expInventory;
            private String memo;
            private String adminMemo;
            private Media.Status status;
            @QueryProjection
            public MediaInfo(
                    Integer id,
                    String name,
                    CompanyDto.Response.Default company,
                    String appKey,
                    String appSecret,
                    String url,
                    List<Category> category,
                    MediaFileDto.Response.FileInfo mediaFiles,
                    Integer expInventory,
                    String memo,
                    String adminMemo,
                    Media.Status status) {
                this.id = id;
                this.name = name;
                this.company = company;
                this.appKey = appKey;
                this.appSecret = appSecret;
                this.url = url;
                this.category = category;
                this.mediaFiles = mediaFiles;
                this.expInventory = expInventory;
                this.memo = memo;
                this.adminMemo = adminMemo;
                this.status = status;
            }
        }

    }
}
