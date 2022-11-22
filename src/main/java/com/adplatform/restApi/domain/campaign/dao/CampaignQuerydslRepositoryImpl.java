package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupCondition;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.dto.*;
import com.adplatform.restApi.domain.creative.dao.CreativeCondition;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.adgroup.domain.QAdGroupSchedule.adGroupSchedule;
import static com.adplatform.restApi.domain.campaign.domain.QAdGoal.adGoal;
import static com.adplatform.restApi.domain.campaign.domain.QAdType.adType;
import static com.adplatform.restApi.domain.campaign.domain.QAdTypeAndGoal.adTypeAndGoal;
import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;
import static com.adplatform.restApi.domain.creative.domain.QCreative.creative;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;
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
    public Page<CampaignDto.Response.Page> search(AdvertiserSearchRequest request, Pageable pageable) {
        List<CampaignDto.Response.Page> content = this.query.select(new QCampaignDto_Response_Page(
                        campaign.id,
                        new QAdTypeAndGoalDto(adType.name, adGoal.name),
                        campaign.name,
                        campaign.dailyBudgetAmount,
                        campaign.config,
                        campaign.systemConfig,
                        campaign.status,
                        campaign.createdAt,
                        campaign.updatedAt,
                        as(select(adGroupSchedule.startDate.min())
                                .from(adGroupSchedule)
                                .join(adGroupSchedule.adGroup, adGroup)
                                .where(adGroup.campaign.id.eq(campaign.id)), "adGroupSchedulesFirstStartDate"),
                        as(select(adGroupSchedule.endDate.max())
                                .from(adGroupSchedule)
                                .join(adGroupSchedule.adGroup, adGroup)
                                .where(adGroup.campaign.id.eq(campaign.id)), "adGroupSchedulesLastEndDate")
                ))
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .leftJoin(campaign.adGroups, adGroup)
                .leftJoin(adGroup.creatives, creative)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        CampaignCondition.inId(request.getCampaignIds()),
                        CampaignCondition.containsName(request.getCampaignName()),
                        CampaignCondition.inConfig(request.getCampaignConfigs()),
                        CampaignCondition.inStatus(request.getCampaignStatuses()),
                        AdGroupCondition.inId(request.getAdGroupIds()),
                        AdGroupCondition.containsName(request.getAdGroupName()),
                        AdGroupCondition.inConfig(request.getAdGroupConfigs()),
                        AdGroupCondition.inStatus(request.getAdGroupStatuses()),
                        CreativeCondition.inId(request.getCreativeIds()),
                        CreativeCondition.containsName(request.getCreativeName()),
                        CreativeCondition.inFormat(request.getCreativeFormats()),
                        CreativeCondition.inConfig(request.getCreativeConfigs()),
                        CreativeCondition.inStatus(request.getCreativeStatuses()),
                        CreativeCondition.inReviewStatus(request.getCreativeReviewStatuses()),
                        AdTypeCondition.inName(request.getAdTypeNames()),
                        AdGoalCondition.inName(request.getAdGoalNames())
                )
                .groupBy(
                        campaign.id,
                        adType.name,
                        adGoal.name,
                        campaign.name,
                        campaign.dailyBudgetAmount,
                        campaign.config,
                        campaign.systemConfig,
                        campaign.status,
                        campaign.createdAt,
                        campaign.updatedAt
                )
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Campaign.class, "campaign", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(campaign.count())
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .leftJoin(campaign.adGroups, adGroup)
                .leftJoin(adGroup.creatives, creative)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        CampaignCondition.inId(request.getCampaignIds()),
                        CampaignCondition.containsName(request.getCampaignName()),
                        CampaignCondition.inConfig(request.getCampaignConfigs()),
                        CampaignCondition.inStatus(request.getCampaignStatuses()),
                        AdGroupCondition.inId(request.getAdGroupIds()),
                        AdGroupCondition.containsName(request.getAdGroupName()),
                        AdGroupCondition.inConfig(request.getAdGroupConfigs()),
                        AdGroupCondition.inStatus(request.getAdGroupStatuses()),
                        CreativeCondition.inId(request.getCreativeIds()),
                        CreativeCondition.containsName(request.getCreativeName()),
                        CreativeCondition.inFormat(request.getCreativeFormats()),
                        CreativeCondition.inConfig(request.getCreativeConfigs()),
                        CreativeCondition.inStatus(request.getCreativeStatuses()),
                        CreativeCondition.inReviewStatus(request.getCreativeReviewStatuses()),
                        AdTypeCondition.inName(request.getAdTypeNames()),
                        AdGoalCondition.inName(request.getAdGoalNames())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

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
                        CampaignCondition.containsName(request.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(campaign.count())
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        campaign.status.ne(Campaign.Status.CANCELED),
                        CampaignCondition.containsName(request.getName()));

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
