package com.adplatform.restApi.placement.dto.placement;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public abstract class PlacementDto {
    public abstract static class Request {

        @Getter
        @Setter
        public static class Search implements PlacementIdGetter {
            @NotNull
            private Integer placementId;

            private Integer mediaId;
            private String name;
            private Integer width;
            private Integer height;
        }
    }

    public abstract static class Response {

        @NoArgsConstructor
        public static class Default extends PlacementDto.Request.Search {
        }

        @Getter
        @Setter
        public static class ForSearchAll {
            @NotNull
            private Integer id;

            private Integer mediaId;
            private String name;
            private Integer width;
            private Integer height;

            @QueryProjection
            public ForSearchAll(
                    Integer id,
                    Integer mediaId,
                    String name,
                    Integer width,
                    Integer height) {
                this.id = id;
                this.mediaId = mediaId;
                this.name = name;
                this.width = width;
                this.height = height;
            }
        }
    }
}
