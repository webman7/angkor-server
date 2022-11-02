package com.adplatform.restApi.domain.user.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
            @NotEmpty
            @Email
            private String id;
            @NotEmpty
            private String password;
            private String name;
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
