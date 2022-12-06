package com.adplatform.restApi.domain.campaign.dao.campaign;

import com.adplatform.restApi.domain.campaign.dao.typegoal.AdGoalCondition;
import com.adplatform.restApi.domain.campaign.dao.typegoal.AdTypeCondition;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.dto.QAdTypeAndGoalDto;
import com.adplatform.restApi.domain.campaign.dto.QCampaignDto_Response_ForSaveAdGroup;
import com.adplatform.restApi.domain.campaign.dto.QCampaignDto_Response_ForUpdate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.campaign.domain.QAdGoal.adGoal;
import static com.adplatform.restApi.domain.campaign.domain.QAdType.adType;
import static com.adplatform.restApi.domain.campaign.domain.QAdTypeAndGoal.adTypeAndGoal;
import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;
import static java.util.Objects.nonNull;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class CampaignQuerydslRepositoryImpl implements CampaignQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<CampaignDto.Response.ForSaveAdGroup> searchForSaveAdGroup(CampaignDto.Request.Search request, Pageable pageable) {
        List<CampaignDto.Response.ForSaveAdGroup> content = this.query
                .select(new QCampaignDto_Response_ForSaveAdGroup(
                        campaign.id,
                        campaign.name,
                        campaign.createdAt,
                        campaign.updatedAt,
                        new QAdTypeAndGoalDto(adType.name, adGoal.name)))
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        campaign.status.ne(Campaign.Status.CANCELED),
                        CampaignCondition.containsName(request.getName()),
                        AdTypeCondition.eqName(request.getAdTypeName()),
                        AdGoalCondition.eqName(request.getAdGoalName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(campaign.count())
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        campaign.status.ne(Campaign.Status.CANCELED),
                        CampaignCondition.containsName(request.getName()),
                        AdTypeCondition.eqName(request.getAdTypeName()),
                        AdGoalCondition.eqName(request.getAdGoalName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public CampaignDto.Response.ForUpdate searchForUpdate(Integer campaignId) {
        return this.query.select(new QCampaignDto_Response_ForUpdate(
                        campaign.id,
                        campaign.name,
                        campaign.dailyBudgetAmount,
                        campaign.goalType,
                        campaign.trackingId,
                        campaign.trackingType
                ))
                .from(campaign)
                .where(campaign.id.eq(campaignId))
                .fetchOne();
    }

    private BooleanExpression eqAdAccountId(Integer adAccountId) {
        return nonNull(adAccountId) ? campaign.adAccount.id.eq(adAccountId) : null;
    }
}
