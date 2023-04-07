package com.adplatform.restApi.domain.media.dto.placement;

import com.adplatform.restApi.domain.media.domain.MediaPlacement;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.domain.statistics.dto.MediaTaxBillPaymentFileDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MediaPlacementDto {
    public abstract static class Request {

        @Getter
        @Setter
        public static class Save {
            private Integer mediaId;
            private Integer placementId;
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String url;
            private List<MultipartFile> mediaPlacementFiles = new ArrayList<>();
            private String memo;
            private String adminMemo;
        }

        @Getter
        @Setter
        public static class Update extends MediaPlacementDto.Request.Save {
            @NotNull
            private Integer id;
        }

        @Getter
        @Setter
        public static class Search {
            private Integer companyId;
            private Integer mediaId;
            private String status;
        }
    }

    public abstract static class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;

            @QueryProjection
            public Default(
                    Integer id
                    ) {
                this.id = id;
            }
        }
        @Getter
        @Setter
        public static class Search {
            @NotNull
            private Integer id;
            private String companyName;
            private String mediaName;
            private String placementName;
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String url;
            private String memo;
            private String adminMemo;
            private MediaPlacement.Status status;
            private String regUserId;
            private LocalDateTime createdAt;
            @QueryProjection
            public Search(
                    Integer id,
                    String companyName,
                    String mediaName,
                    String placementName,
                    String name,
                    Integer width,
                    Integer height,
                    String widthHeightRate,
                    String url,
                    String memo,
                    String adminMemo,
                    MediaPlacement.Status status,
                    String regUserId,
                    LocalDateTime createdAt) {
                this.id = id;
                this.companyName = companyName;
                this.mediaName = mediaName;
                this.placementName = placementName;
                this.name = name;
                this.width = width;
                this.height = height;
                this.widthHeightRate = widthHeightRate;
                this.url = url;
                this.memo = memo;
                this.adminMemo = adminMemo;
                this.status = status;
                this.regUserId = regUserId;
                this.createdAt = createdAt;
            }
        }

        @Getter
        @Setter
        public static class MediaPlacementInfo {
            @NotNull
            private Integer id;
            private Integer companyId;
            private String companyName;
            private Integer mediaId;
            private String mediaName;
            private Integer placementId;
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String url;
            private MediaPlacementFileDto.Response.FileInfo mediaPlacementFiles;
            private String memo;
            private String adminMemo;
            private MediaPlacement.Status status;
            private String regUserId;
            private LocalDateTime createdAt;
        }
    }
}
