package com.adplatform.restApi.domain.adaccount.dto.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class AdAccountUserDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveUser {
            private Integer adAccountId;
            private String userId;
            private AdAccountUser.MemberType memberType;
        }

        @Getter
        @Setter
        public static class UserStatusUpdate {
            private Integer adAccountId;
            private Integer id;
            private AdAccountUser.Status status;
        }

        @Getter
        @Setter
        public static class UserMemberUpdate {
            private Integer adAccountId;
            private Integer id;
            private AdAccountUser.MemberType memberType;
        }

        @Getter
        @Setter
        public static class UserUpdate {
            private Integer adAccountId;
            private Integer id;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class AdAccountUserInfo {
            private Integer id;
            private Integer businessAccountId;
            private UserDto.Response.BaseInfo user;
            private AdAccountUser.MemberType memberType;
            private AdAccountUser.Status status;

            @QueryProjection
            public AdAccountUserInfo(
                    Integer id,
                    Integer businessAccountId,
                    UserDto.Response.BaseInfo user,
                    AdAccountUser.MemberType memberType,
                    AdAccountUser.Status status) {
                this.id = id;
                this.businessAccountId = businessAccountId;
                this.user = user;
                this.memberType = memberType;
                this.status = status;
            }
        }

    }
}
