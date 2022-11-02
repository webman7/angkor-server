package com.adplatform.restApi.domain.adgroup.dto.device;

import lombok.Getter;
import lombok.Setter;

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
