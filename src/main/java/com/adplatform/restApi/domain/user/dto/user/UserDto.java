package com.adplatform.restApi.domain.user.dto.user;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.user.domain.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
public abstract class UserDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Search {
            private String userId;
            private String name;
        }

        @Getter
        @Setter
        public static class UpdateStatus {
            private Integer id;
            private String status;
        }

        @Getter
        @Setter
        public static class MyInfoModify {
            private String name;
            private String phone;
        }

        @Getter
        @Setter
        public static class Modify {
            @NonNull
            private Integer id;
            private String name;
            private String phone;
        }

    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class Detail {
            private Integer id;
            private String loginId;
            private String name;
            private String phone;
            private User.Active active;

//            @QueryProjection
//            public Detail(Integer id, String loginId, String name, String email, String phone, User.Active active, List<String> roles, String company) {
//                this.id = id;
//                this.loginId = loginId;
//                this.name = name;
//                this.email = email;
//                this.phone = phone;
//                this.active = active;
//                this.roles = roles;
//                this.company = company;
//            }
        }

        @Getter
        @Setter
        public static class Search {
            private Integer id;
            private String loginId;
            private String name;
            private String phone;
            private User.Active active;

            @QueryProjection
            public Search(Integer id, String loginId, String name, String phone, User.Active active) {
                this.id = id;
                this.loginId = loginId;
                this.name = name;
                this.phone = phone;
                this.active = active;
            }
        }

        @Getter
        @Setter
        public static class Count {
            private Integer id;
        }

        @Getter
        @Setter
        public static class BaseInfo {
            private Integer id;
            private String loginId;
            private String name;
            private String phone;

            @QueryProjection
            public BaseInfo(Integer id, String loginId, String name, String phone) {
                this.id = id;
                this.loginId = loginId;
                this.name = name;
                this.phone = phone;
            }
        }
    }
}
