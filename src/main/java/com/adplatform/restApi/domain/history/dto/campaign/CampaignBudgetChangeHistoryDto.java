package com.adplatform.restApi.domain.history.dto.campaign;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class CampaignBudgetChangeHistoryDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private int id;
            private int adAccountId;
            private int campaignId;
            private int cashId;
            private Long chgAmount;
            private Long availableAmount;
            private Long availableChgAmount;
            private Long reserveAmount;
            private Long reserveChgAmount;
        }
    }
}