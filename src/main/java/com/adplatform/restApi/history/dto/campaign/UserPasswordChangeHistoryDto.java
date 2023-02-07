package com.adplatform.restApi.history.dto.campaign;

import lombok.Getter;
import lombok.Setter;

public class UserPasswordChangeHistoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private String userId;
            private String userName;
            private String regIp;
        }
    }
}
