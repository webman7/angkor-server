package com.adplatform.restApi.domain.history.dto.user;

import com.adplatform.restApi.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class UserHistoryDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private int userNo;
            private String userId;
            private String userName;
            private String phone;
            private User.Active active;
            private Integer pwdWrongCnt;
            private LocalDateTime pwdUpdatedAt;
            private Integer regUserNo;
            private LocalDateTime createdAt;
            private Integer statusChangedUserNo;
            private LocalDateTime statusChangedAt;
            private Integer updUserNo;
            private LocalDateTime updatedAt;
        }
    }
}
