package com.adplatform.restApi.domain.advertiser.adgroup.dto.device;

import lombok.Getter;
import lombok.Setter;

/**
 * @author junny
 * @since 1.0
 */
public abstract class DeviceDto {
    public static abstract class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;
            private String name;
        }
    }
}
