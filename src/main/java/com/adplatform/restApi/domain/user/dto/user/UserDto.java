package com.adplatform.restApi.domain.user.dto.user;

import com.adplatform.restApi.domain.user.domain.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public abstract class UserDto {
    public static abstract class Request {
        public static class Search {

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
            private List<String> roles;
            private String company;

            @QueryProjection
            public Detail(Integer id, String loginId, String name, String email, String phone, User.Active active, List<String> roles, String company) {
                this.id = id;
                this.loginId = loginId;
                this.name = name;
                this.email = email;
                this.phone = phone;
                this.active = active;
                this.roles = roles;
                this.company = company;
            }
        }
    }
}
