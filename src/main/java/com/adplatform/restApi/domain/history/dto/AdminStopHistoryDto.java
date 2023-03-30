package com.adplatform.restApi.domain.history.dto;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.history.domain.AdminStopHistory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AdminStopHistoryDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private AdminStopHistory.Type type;
            private Integer stopId;
            private String reason;
        }
    }
}
