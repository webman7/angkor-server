package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.dto.AdTypeAndGoalDto;
import com.adplatform.restApi.domain.campaign.dto.QAdTypeAndGoalDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.campaign.domain.QAdGoal.adGoal;
import static com.adplatform.restApi.domain.campaign.domain.QAdType.adType;
import static com.adplatform.restApi.domain.campaign.domain.QAdTypeAndGoal.adTypeAndGoal;
import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;

@RequiredArgsConstructor
@Repository
public class AdTypeAndGoalQuerydslRepositoryImpl implements AdTypeAndGoalQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public AdTypeAndGoalDto findByCampaignId(Integer campaignId) {
        return this.query.select(new QAdTypeAndGoalDto(adType.name, adGoal.name))
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .where(campaign.id.eq(campaignId))
                .fetchOne();
    }
}
