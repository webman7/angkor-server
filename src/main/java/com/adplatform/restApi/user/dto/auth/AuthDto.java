package com.adplatform.restApi.user.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public abstract class AuthDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Login {
            @Email
            private String id;
            @NotBlank
            private String password;
        }

        @Getter
        @Setter
        public static class SignUp {
            @NotNull
            private Integer companyId;
            @NotEmpty
            @Email
            private String id;
            @NotBlank
            private String name;
            @NotBlank
            private String password1;
            @NotBlank
            private String password2;
            private String phone;
        }

        @Getter
        @Setter
        public static class FindPassword {
            @NotEmpty
            private String name;
            @NotEmpty
            @Email
            private String id;
        }

        @Getter
        @Setter
        public static class ChangePassword {
            @NotEmpty
            private String password1;
            @NotEmpty
            private String password2;
        }
    }

    public static abstract class Response {
    }
}
