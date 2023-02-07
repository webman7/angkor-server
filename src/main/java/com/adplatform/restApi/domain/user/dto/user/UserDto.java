package com.adplatform.restApi.domain.user.dto.user;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public abstract class UserDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Search {
            private String name;
        }

        @Getter
        @Setter
        public static class UpdateStatus {
            private Integer id;
            private String status;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class Detail {
            private Integer id;
            private String loginId;
            private String name;
            private String email;
            private String phone;
            private User.Active active;
            private List<Integer> roles;
            private Company company;

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
            private String email;
            private String phone;
            private String active;
            private String roles;
            private String company;

        }
    }
}
