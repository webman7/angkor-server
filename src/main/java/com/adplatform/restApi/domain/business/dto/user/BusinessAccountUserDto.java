package com.adplatform.restApi.domain.business.dto.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class BusinessAccountUserDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveUser {
            private Integer businessAccountId;
            private String userId;
            private BusinessAccountUser.MemberType memberType;
        }

        @Getter
        @Setter
        public static class UserStatusUpdate {
            private Integer businessAccountId;
            private Integer id;
            private BusinessAccountUser.Status status;
        }

        @Getter
        @Setter
        public static class UserMemberUpdate {
            private Integer businessAccountId;
            private Integer id;
            private BusinessAccountUser.MemberType memberType;
        }

        @Getter
        @Setter
        public static class UserUpdate {
            private Integer businessAccountId;
            private Integer prevId;
            private Integer id;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class BusinessAccountUserInfo {
            private Integer id;
            private UserDto.Response.BaseInfo user;
            private BusinessAccountUser.MemberType memberType;
            private BusinessAccountUser.AccountingYN accountingYN;
            private BusinessAccountUser.Status status;

            @QueryProjection
            public BusinessAccountUserInfo(
                    Integer id,
                    UserDto.Response.BaseInfo user,
                    BusinessAccountUser.MemberType memberType,
                    BusinessAccountUser.AccountingYN accountingYN,
                    BusinessAccountUser.Status status) {
                this.id = id;
                this.user = user;
                this.memberType = memberType;
                this.accountingYN = accountingYN;
                this.status = status;
            }
        }

    }
}
