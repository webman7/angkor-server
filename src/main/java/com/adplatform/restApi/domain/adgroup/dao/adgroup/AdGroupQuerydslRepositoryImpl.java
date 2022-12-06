package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.QAdGroupDto_Response_AdvertiserSearch;
import com.adplatform.restApi.domain.campaign.dao.typegoal.AdGoalCondition;
import com.adplatform.restApi.domain.campaign.dao.typegoal.AdTypeCondition;
import com.adplatform.restApi.domain.campaign.dao.campaign.CampaignCondition;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.creative.dao.CreativeCondition;
import com.adplatform.restApi.domain.statistics.dto.QReportConversionInformationResponse;
import com.adplatform.restApi.domain.statistics.dto.QReportInformationResponse;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
import static com.adplatform.restApi.domain.campaign.domain.QAdGoal.adGoal;
import static com.adplatform.restApi.domain.campaign.domain.QAdType.adType;
import static com.adplatform.restApi.domain.campaign.domain.QAdTypeAndGoal.adTypeAndGoal;
import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;
import static com.adplatform.restApi.domain.creative.domain.QCreative.creative;
import static com.adplatform.restApi.domain.statistics.domain.report.QReportAdGroupConversionDaily.reportAdGroupConversionDaily;
import static com.adplatform.restApi.domain.statistics.domain.report.QReportAdGroupDaily.reportAdGroupDaily;
import static com.querydsl.jpa.JPAExpressions.select;
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
    public Page<AdGroupDto.Response.AdvertiserSearch> search(AdvertiserSearchRequest request, Pageable pageable) {
        System.out.println(request.getAdAccountId());
        System.out.println(request.getReportStartDate());
        System.out.println(request.getReportEndDate());
        List<AdGroupDto.Response.AdvertiserSearch> content = this.query.select(new QAdGroupDto_Response_AdvertiserSearch(
                        adGroup.id,
                        adType.name,
                        adGoal.name,
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
                        adGroup.updatedAt,
                        new QReportInformationResponse(
                                this.getReportSubQuery(reportAdGroupDaily.information.cost, request, "cost"),
                                this.getReportSubQuery(reportAdGroupDaily.information.impression, request, "impression"),
                                this.getReportSubQuery(reportAdGroupDaily.information.click, request, "click"),
                                this.getReportSubQuery(reportAdGroupDaily.information.reach, request, "reach"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoAutoPlay, request, "videoAutoPlay"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoTouches, request, "videoTouches"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoUnmute, request, "videoUnmute"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay3Seconds, request, "videoPlay3Seconds"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay5Seconds, request, "videoPlay5Seconds"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay10Seconds, request, "videoPlay10Seconds"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay15Seconds, request, "videoPlay15Seconds"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay30Seconds, request, "videoPlay30Seconds"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay60Seconds, request, "videoPlay60Seconds"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay25Percent, request, "videoPlay25Percent"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay50Percent, request, "videoPlay50Percent"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay75Percent, request, "videoPlay75Percent"),
                                this.getReportSubQuery(reportAdGroupDaily.information.videoPlay100Percent, request, "videoPlay100Percent")
                        ),
                        new QReportConversionInformationResponse(
                                this.getReportConversionSubQuery(reportAdGroupConversionDaily.information.signUpDay1, request),
                                this.getReportConversionSubQuery(reportAdGroupConversionDaily.information.signUpDay7, request),
                                this.getReportConversionSubQuery(reportAdGroupConversionDaily.information.purchaseDay1, request),
                                this.getReportConversionSubQuery(reportAdGroupConversionDaily.information.purchaseDay7, request),
                                this.getReportConversionSubQuery(reportAdGroupConversionDaily.information.viewCartDay1, request),
                                this.getReportConversionSubQuery(reportAdGroupConversionDaily.information.viewCartDay7, request)
                        )
                ))
                .from(adGroup)
                .join(adGroup.campaign, campaign).on(
                        CampaignCondition.inConfigElseWithOutStatusDel(request.getCampaignConfigs()),
                        CampaignCondition.inStatusElseWithOutStatusDel(request.getCampaignStatuses())
                )
                .leftJoin(adGroup.creatives, creative).on(
                        CreativeCondition.inConfigElseWithOutStatusDel(request.getCreativeConfigs()),
                        CreativeCondition.inStatusElseWithOutStatusDel(request.getCreativeStatuses())
                )
                .join(adGroup.adGroupSchedule, adGroupSchedule)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        CampaignCondition.inId(request.getCampaignIds()),
                        CampaignCondition.containsName(request.getCampaignName()),
                        AdGroupCondition.inId(request.getAdGroupIds()),
                        AdGroupCondition.containsName(request.getAdGroupName()),
                        AdGroupCondition.inConfigElseWithOutStatusDel(request.getAdGroupConfigs()),
                        AdGroupCondition.inStatusElseWithOutStatusDel(request.getAdGroupStatuses()),
                        CreativeCondition.inId(request.getCreativeIds()),
                        CreativeCondition.containsName(request.getCreativeName()),
                        CreativeCondition.inFormat(request.getCreativeFormats()),
                        CreativeCondition.inReviewStatus(request.getCreativeReviewStatuses()),
                        AdTypeCondition.inName(request.getAdTypeNames()),
                        AdGoalCondition.inName(request.getAdGoalNames())
                )
                .groupBy(
                        adGroup.id,
                        adType.name,
                        adGoal.name,
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
                        adGroup.updatedAt
                )
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdGroup.class, "adGroup", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adGroup.countDistinct())
                .from(adGroup)
                .join(adGroup.campaign, campaign).on(
                        CampaignCondition.inConfigElseWithOutStatusDel(request.getCampaignConfigs()),
                        CampaignCondition.inStatusElseWithOutStatusDel(request.getCampaignStatuses())
                )
                .leftJoin(adGroup.creatives, creative).on(
                        CreativeCondition.inConfigElseWithOutStatusDel(request.getCreativeConfigs()),
                        CreativeCondition.inStatusElseWithOutStatusDel(request.getCreativeStatuses())
                )
                .join(adGroup.adGroupSchedule, adGroupSchedule)
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        CampaignCondition.inId(request.getCampaignIds()),
                        CampaignCondition.containsName(request.getCampaignName()),
                        AdGroupCondition.inId(request.getAdGroupIds()),
                        AdGroupCondition.containsName(request.getAdGroupName()),
                        AdGroupCondition.inConfigElseWithOutStatusDel(request.getAdGroupConfigs()),
                        AdGroupCondition.inStatusElseWithOutStatusDel(request.getAdGroupStatuses()),
                        CreativeCondition.inId(request.getCreativeIds()),
                        CreativeCondition.containsName(request.getCreativeName()),
                        CreativeCondition.inFormat(request.getCreativeFormats()),
                        CreativeCondition.inReviewStatus(request.getCreativeReviewStatuses()),
                        AdTypeCondition.inName(request.getAdTypeNames()),
                        AdGoalCondition.inName(request.getAdGoalNames())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Expression<Integer> getReportSubQuery(NumberPath<Integer> path, AdvertiserSearchRequest request, String alias) {
        return ExpressionUtils.as(select(path.sum())
                .from(reportAdGroupDaily)
                .where(adGroup.id.eq(reportAdGroupDaily.adGroupId),
                        reportAdGroupDaily.reportDate.between(request.getReportStartDate(), request.getReportEndDate())),
                alias);
    }

    private JPQLQuery<Integer> getReportConversionSubQuery(NumberPath<Integer> path, AdvertiserSearchRequest request) {
        return select(path.sum())
                .from(reportAdGroupConversionDaily)
                .where(adGroup.id.eq(reportAdGroupConversionDaily.adGroupId),
                        reportAdGroupConversionDaily.reportDate.between(request.getReportStartDate(), request.getReportEndDate()));
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
                        adGroup.status.ne(AdGroup.Status.CANCELED),
                        AdGroupCondition.containsName(request.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adGroup.count())
                .from(adGroup)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        adGroup.status.ne(AdGroup.Status.CANCELED),
                        AdGroupCondition.containsName(request.getName()));

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
}
