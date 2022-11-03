package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.adgroup.domain.QAdGroupSchedule.adGroupSchedule;

@RequiredArgsConstructor
@Repository
public class AdGroupQuerydslRepositoryImpl implements AdGroupQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Map<Integer, Integer> findScheduleFirstStartDateByCampaignId(List<Integer> campaignIds) {
        return this.query.select(adGroup.campaign.id, adGroupSchedule.startDate.min())
                .from(adGroup)
                .join(adGroup.adGroupSchedule, adGroupSchedule)
                .where(adGroup.campaign.id.in(campaignIds))
                .groupBy(adGroup.campaign.id)
                .transform(GroupBy.groupBy(adGroup.campaign.id).as(adGroupSchedule.startDate.min()));
    }

    @Override
    public Map<Integer, Integer> findScheduleLastEndDateByCampaignId(List<Integer> campaignIds) {
        return this.query.select(adGroup.campaign.id, adGroupSchedule.endDate.max())
                .from(adGroup)
                .join(adGroup.adGroupSchedule, adGroupSchedule)
                .where(adGroup.campaign.id.in(campaignIds))
                .groupBy(adGroup.campaign.id)
                .transform(GroupBy.groupBy(adGroup.campaign.id).as(adGroupSchedule.endDate.max()));
    }
}
