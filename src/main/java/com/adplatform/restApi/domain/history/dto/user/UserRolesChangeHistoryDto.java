package com.adplatform.restApi.domain.history.dto.user;

import lombok.Getter;
import lombok.Setter;

public class UserRolesChangeHistoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private String prevRoles;
            private int userNo;
            private String roles;
        }
    }
}
