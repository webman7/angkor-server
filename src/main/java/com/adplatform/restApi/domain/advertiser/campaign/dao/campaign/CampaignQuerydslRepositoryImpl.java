package com.adplatform.restApi.domain.advertiser.campaign.dao.campaign;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.campaign.dao.typegoal.AdGoalCondition;
import com.adplatform.restApi.domain.advertiser.campaign.dao.typegoal.AdTypeCondition;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.advertiser.campaign.dto.*;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroupSchedule.adGroupSchedule;
import static com.adplatform.restApi.domain.advertiser.campaign.domain.QAdGoal.adGoal;
import static com.adplatform.restApi.domain.advertiser.campaign.domain.QAdType.adType;
import static com.adplatform.restApi.domain.advertiser.campaign.domain.QAdTypeAndGoal.adTypeAndGoal;
import static com.adplatform.restApi.domain.advertiser.campaign.domain.QCampaign.campaign;
import static java.util.Objects.nonNull;

/**
 * @author junny
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
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
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
                        campaign.budgetAmount,
                        campaign.goalType,
                        campaign.trackingId,
                        campaign.trackingType
                ))
                .from(campaign)
                .where(campaign.id.eq(campaignId))
                .fetchOne();
    }

    @Override
    public CampaignDto.Response.ForDateSave dateForSave(Integer campaignId) {
        return this.query.select(new QCampaignDto_Response_ForDateSave(
                        campaign.id,
                        adGroupSchedule.startDate.min(),
                        adGroupSchedule.endDate.max()
                ))
                .from(campaign, adGroup)
                .leftJoin(adGroupSchedule)
                .on(adGroup.id.eq(adGroupSchedule.adGroup.id))
                .where(campaign.id.eq(adGroup.campaign.id),
                       campaign.id.eq(campaignId),
                       campaign.config.ne(Campaign.Config.DEL),
                       adGroup.config.ne(AdGroup.Config.DEL))
                .fetchOne();
    }

    @Override
    public CampaignDto.Response.ForDateUpdate dateForUpdate(Integer adGroupId) {
        return this.query.select(new QCampaignDto_Response_ForDateUpdate(
                        campaign.id,
                        adGroupSchedule.startDate.min(),
                        adGroupSchedule.endDate.max()
                ))
                .from(campaign, adGroup)
                .leftJoin(adGroupSchedule)
                .on(adGroup.id.eq(adGroupSchedule.adGroup.id))
                .where(campaign.id.eq(adGroup.campaign.id),
                       adGroup.id.eq(adGroupId),
                       campaign.config.ne(Campaign.Config.DEL),
                       adGroup.config.ne(AdGroup.Config.DEL))
                .fetchOne();
    }

    @Override
    public List<CampaignDto.Response.Budget> getBudget(Integer id) {
        return this.query.select(new QCampaignDto_Response_Budget(campaign.id, campaign.budgetAmount))
                .from(campaign)
                .where(campaign.id.eq(id))
                .fetch();
    }

    @Override
    public CampaignDto.Response.CampaignByAdAccountId getCampaignByAdAccountId(Integer id) {
        return this.query.select(new QCampaignDto_Response_CampaignByAdAccountId(campaign.id, adAccount.businessAccount.id, campaign.adAccount.id, campaign.budgetAmount))
                .from(campaign, adAccount)
                .where(campaign.id.eq(id),
                campaign.adAccount.id.eq(adAccount.id))
                .fetchOne();
    }

    @Override
    public CampaignDto.Response.CampaignByBusinessAccountId getCampaignByBusinessAccountId(Integer id) {
        return this.query.select(new QCampaignDto_Response_CampaignByBusinessAccountId(adAccount.businessAccount.id, campaign.adAccount.id, campaign.budgetAmount))
                .from(campaign, adAccount)
                .where(adAccount.id.eq(id),
                        campaign.adAccount.id.eq(adAccount.id))
                .fetchOne();
    }


    private BooleanExpression eqAdAccountId(Integer adAccountId) {
        return nonNull(adAccountId) ? campaign.adAccount.id.eq(adAccountId) : null;
    }
}
