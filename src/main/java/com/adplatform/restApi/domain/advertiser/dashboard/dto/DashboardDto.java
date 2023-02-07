package com.adplatform.restApi.domain.advertiser.dashboard.dto;

import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdvertiserSearchAdGroupCondition;
import com.adplatform.restApi.domain.advertiser.campaign.dto.AdvertiserSearchCampaignCondition;
import com.adplatform.restApi.domain.advertiser.creative.dto.AdvertiserSearchCreativeCondition;
import com.adplatform.restApi.domain.statistics.dto.ReportDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

public class DashboardDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class AdAccountDashboard {
            private Integer adAccountId;
        }

        @Getter
        @Setter
        public static class DashboardChart {
            private Integer adAccountId;
            private Integer startDate;
            private Integer endDate;
            private List<String> indicators;
        }

        @Getter
        @Setter
        public static class TotalDashboardChart {
            private Integer adAccountId;
            private Integer startDate;
            private Integer endDate;
            private List<String> indicators;
            @Valid
            private AdvertiserSearchCampaignCondition campaignCondition;
            @Valid
            private AdvertiserSearchAdGroupCondition adGroupCondition;
            @Valid
            private AdvertiserSearchCreativeCondition creativeCondition;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class AdAccountCountByAd {
            private Long campaignCount;
            private Long adGroupCount;
            private Long creativeCount;

            @QueryProjection
            public AdAccountCountByAd(
                    Long campaignCount,
                    Long adGroupCount,
                    Long creativeCount
            ) {
                this.campaignCount = campaignCount;
                this.adGroupCount = adGroupCount;
                this.creativeCount = creativeCount;
            }
        }

        @Getter
        @Setter
        public static class DashboardChart {
            private Integer reportDate;
            private ReportDto.Response report;
        }

        @Getter
        @Setter
        public static class AdAccountDashboardCost {
            private Integer costOfToday;
        }
    }
}
