package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.dto.QAdTypeAndGoalDto;
import com.adplatform.restApi.domain.campaign.dto.QCampaignDto_Response_ForSaveAdGroup;
import com.adplatform.restApi.domain.campaign.dto.QCampaignDto_Response_Page;
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
import java.util.Objects;

import static com.adplatform.restApi.domain.campaign.domain.QAdGoal.adGoal;
import static com.adplatform.restApi.domain.campaign.domain.QAdType.adType;
import static com.adplatform.restApi.domain.campaign.domain.QAdTypeAndGoal.adTypeAndGoal;
import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;

@RequiredArgsConstructor
@Repository
public class CampaignQuerydslRepositoryImpl implements CampaignQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<CampaignDto.Response.Page> search(CampaignDto.Request.Search request, Pageable pageable) {
        List<CampaignDto.Response.Page> content = this.query.select(new QCampaignDto_Response_Page(
                        campaign.id,
                        new QAdTypeAndGoalDto(adType.name, adGoal.name),
                        campaign.name,
                        campaign.dailyBudgetAmount,
                        campaign.config,
                        campaign.systemConfig,
                        campaign.status,
                        campaign.createdAt,
                        campaign.updatedAt
                ))
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Campaign.class, "campaign", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .where(this.eqAdAccountId(request.getAdAccountId()), this.containsName(request.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(campaign.count())
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .where(this.eqAdAccountId(request.getAdAccountId()), this.containsName(request.getName()));

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
                        this.containsName(request.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(campaign.count())
                .from(campaign)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        campaign.status.ne(Campaign.Status.CANCELED),
                        this.containsName(request.getName()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqAdAccountId(Integer adAccountId) {
        return Objects.nonNull(adAccountId) ? campaign.adAccount.id.eq(adAccountId) : null;
    }

    private BooleanExpression containsName(String name) {
        return Objects.nonNull(name) ? campaign.name.contains(name) : null;
    }
}
