package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.QAdGroupDto_Response_Default;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.adgroup.domain.QAdGroupDemographicTarget.adGroupDemographicTarget;
import static com.adplatform.restApi.domain.adgroup.domain.QAdGroupSchedule.adGroupSchedule;
import static com.adplatform.restApi.domain.adgroup.domain.QDevice.device;
import static com.adplatform.restApi.domain.adgroup.domain.QMedia.media;
import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;
import static java.util.Objects.nonNull;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class AdGroupQuerydslRepositoryImpl implements AdGroupQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<AdGroupDto.Response.Default> search(AdGroupDto.Request.Search request, Pageable pageable) {
        List<AdGroupDto.Response.Default> content = this.query.select(new QAdGroupDto_Response_Default(
                        adGroup.id,
                        adGroup.name,
                        adGroup.pacing,
                        adGroup.pacingType,
                        adGroup.bidAmount,
                        adGroup.bidStrategy,
                        adGroup.dailyBudgetAmount,
                        adGroup.config,
                        adGroup.systemConfig,
                        adGroup.status,
                        adGroup.campaign.id,
                        campaign.name,
                        adGroupSchedule.startDate,
                        adGroupSchedule.endDate,
                        adGroup.createdAt,
                        adGroup.updatedAt))
                .from(adGroup)
                .join(adGroup.campaign, campaign)
                .join(adGroup.adGroupSchedule, adGroupSchedule)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        this.eqCampaignId(request.getCampaignId()),
                        this.containsName(request.getName()))
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdGroup.class, "adGroup", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adGroup.count())
                .from(adGroup)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        this.eqCampaignId(request.getCampaignId()),
                        this.containsName(request.getName()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<AdGroupDto.Response.ForSaveCreative> searchForSaveCreative(AdGroupDto.Request.Search request, Pageable pageable) {
        List<AdGroupDto.Response.ForSaveCreative> content = this.query.select(Projections.constructor(
                        AdGroupDto.Response.ForSaveCreative.class,
                        adGroup.id, adGroup.name, campaign.id, campaign.name))
                .from(adGroup)
                .join(adGroup.campaign, campaign)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        adGroup.status.ne(Campaign.Status.CANCELED),
                        this.containsName(request.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adGroup.count())
                .from(adGroup)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        adGroup.status.ne(Campaign.Status.CANCELED),
                        this.containsName(request.getName()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<AdGroup> findByIdFetchJoin(Integer id) {
        return Optional.ofNullable(this.query.selectFrom(adGroup)
                .join(adGroup.demographicTarget, adGroupDemographicTarget).fetchJoin()
                .join(adGroup.adGroupSchedule, adGroupSchedule).fetchJoin()
                .leftJoin(adGroup.media, media).fetchJoin()
                .leftJoin(adGroup.devices, device).fetchJoin()
                .where(adGroup.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression eqAdAccountId(Integer adAccountId) {
        return nonNull(adAccountId) ? adGroup.campaign.adAccount.id.eq(adAccountId) : null;
    }

    private BooleanExpression eqCampaignId(Integer campaignId) {
        return nonNull(campaignId) ? campaign.id.eq(campaignId) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.isNotBlank(name) ? adGroup.name.contains(name) : null;
    }
}
