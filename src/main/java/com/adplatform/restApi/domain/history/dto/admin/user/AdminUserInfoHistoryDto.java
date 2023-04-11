package com.adplatform.restApi.domain.history.dto.admin.user;

import com.adplatform.restApi.domain.company.domain.AdminUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AdminUserInfoHistoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private int id;
            private int companyId;
            private int userNo;
            private AdminUser.MemberType memberType;
            private AdminUser.Status status;
            private int regUserNo;
            private LocalDateTime createdAt;
            private int updUserNo;
            private LocalDateTime updatedAt;
        }
    }
}
