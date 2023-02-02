package com.adplatform.restApi.advertiser.campaign.dao.typegoal;

import com.adplatform.restApi.advertiser.campaign.dto.AdTypeAndGoalDto;
import com.adplatform.restApi.advertiser.campaign.dto.QAdTypeAndGoalDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.advertiser.campaign.domain.QAdGoal.adGoal;
import static com.adplatform.restApi.advertiser.campaign.domain.QAdType.adType;
import static com.adplatform.restApi.advertiser.campaign.domain.QAdTypeAndGoal.adTypeAndGoal;
import static com.adplatform.restApi.advertiser.campaign.domain.QCampaign.campaign;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
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
