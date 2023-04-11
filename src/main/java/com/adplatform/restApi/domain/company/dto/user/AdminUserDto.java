package com.adplatform.restApi.domain.company.dto.user;

import com.adplatform.restApi.domain.company.domain.AdminUser;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class AdminUserDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveUser {
            private Integer companyId;
            private String userId;
            private AdminUser.MemberType memberType;
        }

        @Getter
        @Setter
        public static class UserStatusUpdate {
            private Integer companyId;
            private Integer id;
            private AdminUser.Status status;
        }

        @Getter
        @Setter
        public static class UserMemberUpdate {
            private Integer companyId;
            private Integer id;
            private AdminUser.MemberType memberType;
        }

        @Getter
        @Setter
        public static class UserUpdate {
            private Integer companyId;
            private Integer id;
        }

        @Getter
        @Setter
        public static class SearchAdminUser {
            private String userId;
            private String name;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class AdminUserInfo {
            private Integer id;
            private UserDto.Response.BaseInfo user;
            private AdminUser.MemberType memberType;
            private AdminUser.Status status;

            @QueryProjection
            public AdminUserInfo(
                    Integer id,
                    UserDto.Response.BaseInfo user,
                    AdminUser.MemberType memberType,
                    AdminUser.Status status) {
                this.id = id;
                this.user = user;
                this.memberType = memberType;
                this.status = status;
            }
        }

    }
}
