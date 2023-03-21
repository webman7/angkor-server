package com.adplatform.restApi.domain.media.dto.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MediaCategoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            @NotBlank
            private Integer mediaId;
            @NotBlank
            private Integer categoryId;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer mediaId;
            private Integer categoryId;
        }
    }
}
