package com.adplatform.restApi.domain.adgroup.dto.media;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public abstract class MediaDto {
    public static abstract class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;
            private String name;
            private String appKey;
            private String appSecret;
        }
    }
}
