package com.adplatform.restApi.domain.history.dto.campaign;

import lombok.Getter;
import lombok.Setter;

public class UserLoginHistoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private int loginType;
            private String userId;
            private String regIp;
        }
    }
}
