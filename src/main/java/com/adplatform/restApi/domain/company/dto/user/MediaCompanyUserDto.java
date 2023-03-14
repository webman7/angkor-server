package com.adplatform.restApi.domain.company.dto.user;

import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class MediaCompanyUserDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveUser {
            private Integer companyId;
            private String userId;
            private MediaCompanyUser.MemberType memberType;
        }

        @Getter
        @Setter
        public static class UserStatusUpdate {
            private Integer companyId;
            private Integer id;
            private MediaCompanyUser.Status status;
        }

        @Getter
        @Setter
        public static class UserMemberUpdate {
            private Integer companyId;
            private Integer id;
            private MediaCompanyUser.MemberType memberType;
        }

        @Getter
        @Setter
        public static class UserUpdate {
            private Integer companyId;
            private Integer id;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class MediaCompanyUserInfo {
            private Integer id;
            private UserDto.Response.BaseInfo user;
            private MediaCompanyUser.MemberType memberType;
            private MediaCompanyUser.AccountingYN accountingYN;
            private MediaCompanyUser.Status status;

            @QueryProjection
            public MediaCompanyUserInfo(
                    Integer id,
                    UserDto.Response.BaseInfo user,
                    MediaCompanyUser.MemberType memberType,
                    MediaCompanyUser.AccountingYN accountingYN,
                    MediaCompanyUser.Status status) {
                this.id = id;
                this.user = user;
                this.memberType = memberType;
                this.accountingYN = accountingYN;
                this.status = status;
            }
        }

    }
}
