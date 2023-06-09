package com.adplatform.restApi.domain.media.dto.placement;

import com.adplatform.restApi.domain.media.domain.Placement;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class PlacementDto {
    public abstract static class Request {

        @Getter
        @Setter
        public static class Save {
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String memo;
            private String adminMemo;
        }

        @Getter
        @Setter
        public static class Update {
            private Integer id;
            private String name;
            private String memo;
            private String adminMemo;
        }

        @Getter
        @Setter
        public static class Approve {
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String memo;
            private String adminMemo;
            private Integer approveUserNo;
            private LocalDateTime approveAt;
        }

        @Getter
        @Setter
        public static class Search {
            @NotNull
            private Integer id;
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String memo;
            private String adminMemo;
        }
//
//        @Getter
//        @Setter
//        public static class Insert {
//            @NotNull
//            private Integer id;
//            private String name;
//            private Integer width;
//            private Integer height;
//            private String widthHeightRate;
//            private String memo;
//            private String adminMemo;
//            private Placement.Status status;
//        }
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
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String memo;
            private String adminMemo;
            private Placement.Status status;
            private String regUserId;
            private LocalDateTime createdAt;

            @QueryProjection
            public Search(
                    Integer id,
                    String name,
                    Integer width,
                    Integer height,
                    String widthHeightRate,
                    String memo,
                    String adminMemo,
                    Placement.Status status,
                    String regUserId,
                    LocalDateTime createdAt) {
                this.id = id;
                this.name = name;
                this.width = width;
                this.height = height;
                this.widthHeightRate = widthHeightRate;
                this.memo = memo;
                this.adminMemo = adminMemo;
                this.status = status;
                this.regUserId = regUserId;
                this.createdAt = createdAt;
            }
        }

        @Getter
        @Setter
        public static class ForSearchAll {
            @NotNull
            private Integer id;
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String memo;
            private String adminMemo;

            @QueryProjection
            public ForSearchAll(
                    Integer id,
                    String name,
                    Integer width,
                    Integer height,
                    String widthHeightRate,
                    String memo,
                    String adminMemo) {
                this.id = id;
                this.name = name;
                this.width = width;
                this.height = height;
                this.widthHeightRate = widthHeightRate;
                this.memo = memo;
                this.adminMemo = adminMemo;
            }
        }

        @Getter
        @Setter
        public static class PlacementInfo {
            @NotNull
            private Integer id;
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String memo;
            private String adminMemo;
            private Placement.Status status;
        }
    }
}
