package com.adplatform.restApi.domain.history.dto.adaccount.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AdAccountUserInfoHistoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private int id;
            private int adAccountId;
            private int userNo;
            private AdAccountUser.MemberType memberType;
            private AdAccountUser.Status status;
            private int regUserNo;
            private LocalDateTime createdAt;
            private int updUserNo;
            private LocalDateTime updatedAt;
        }
    }
}
