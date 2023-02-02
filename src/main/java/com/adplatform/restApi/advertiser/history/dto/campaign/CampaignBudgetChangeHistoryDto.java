package com.adplatform.restApi.advertiser.history.dto.campaign;

import lombok.Getter;
import lombok.Setter;

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
