package com.adplatform.restApi.domain.history.dto.business.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class BusinessAccountUserInfoHistoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private int id;
            private int businessAccountId;
            private int userNo;
            private BusinessAccountUser.MemberType memberType;
            private BusinessAccountUser.AccountingYN accountingYN;
            private BusinessAccountUser.Status status;
            private int regUserNo;
            private LocalDateTime createdAt;
            private int updUserNo;
            private LocalDateTime updatedAt;
        }
    }
}
