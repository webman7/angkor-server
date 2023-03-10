package com.adplatform.restApi.domain.business.dto.user;

import lombok.Getter;
import lombok.Setter;

public class BusinessAccountUserDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveUser {
            private Integer id;
        }
    }

    public static abstract class Response {

    }
}
