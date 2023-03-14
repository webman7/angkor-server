package com.adplatform.restApi.domain.history.dto.mediaCompany.user;

import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MediaCompanyUserInfoHistoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private int id;
            private int companyId;
            private int userNo;
            private MediaCompanyUser.MemberType memberType;
            private MediaCompanyUser.AccountingYN accountingYN;
            private MediaCompanyUser.Status status;
            private int regUserNo;
            private LocalDateTime createdAt;
            private int updUserNo;
            private LocalDateTime updatedAt;
        }
    }
}
