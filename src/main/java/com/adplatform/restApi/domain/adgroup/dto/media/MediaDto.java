package com.adplatform.restApi.domain.adgroup.dto.media;

import lombok.Getter;
import lombok.Setter;

public abstract class MediaDto {
    public static abstract class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;
            private String name;
        }
    }
}
