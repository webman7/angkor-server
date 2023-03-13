package com.adplatform.restApi.domain.user.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author junny
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
            @Email
            private String id;
        }

        @Getter
        @Setter
        public static class FindPasswordCert {
            @NotEmpty
            @Email
            private String id;
        }

        @Getter
        @Setter
        public static class FindPasswordConfirm {
            @NotEmpty
            @Email
            private String id;
            @NotEmpty
            private String certNo;
        }

        @Getter
        @Setter
        public static class FindPasswordChange {
            @NotEmpty
            @Email
            private String id;
            @NotEmpty
            private String certNo;
            private String historyId;
            @NotEmpty
            private String password;
        }

        @Getter
        @Setter
        public static class ChangePassword {
            @NotEmpty
            private String oldPassword;
            @NotEmpty
            private String newPassword;
        }
    }

    public static abstract class Response {

        @Getter
        @Setter
        public static class FindPasswordCert {
            private String id;
            private String name;
            private String historyId;
        }
    }
}
