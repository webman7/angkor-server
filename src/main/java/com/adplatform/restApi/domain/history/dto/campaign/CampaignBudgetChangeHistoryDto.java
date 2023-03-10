package com.adplatform.restApi.domain.history.dto.campaign;

import lombok.Getter;
import lombok.Setter;

public class CampaignBudgetChangeHistoryDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private int id;
            private int businessAccountId;
            private int adAccountId;
            private int campaignId;
            private Float chgAmount;
            private Float availableAmount;
            private Float availableChgAmount;
            private Float reserveAmount;
            private Float reserveChgAmount;
        }
    }
}
