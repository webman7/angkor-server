package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupCondition;
import com.adplatform.restApi.domain.campaign.dao.AdGoalCondition;
import com.adplatform.restApi.domain.campaign.dao.AdTypeCondition;
import com.adplatform.restApi.domain.campaign.dao.CampaignCondition;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.creative.dto.QCreativeDto_Response_Default;
import com.adplatform.restApi.domain.statistics.dto.QReportConversionInformationResponse;
import com.adplatform.restApi.domain.statistics.dto.QReportInformationResponse;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
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
import java.util.Objects;
import java.util.Optional;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.campaign.domain.QAdGoal.adGoal;
import static com.adplatform.restApi.domain.campaign.domain.QAdType.adType;
import static com.adplatform.restApi.domain.campaign.domain.QAdTypeAndGoal.adTypeAndGoal;
import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;
import static com.adplatform.restApi.domain.creative.domain.QCreative.creative;
import static com.adplatform.restApi.domain.creative.domain.QCreativeFile.creativeFile;
import static com.adplatform.restApi.domain.creative.domain.QCreativeOpinionProofFile.creativeOpinionProofFile;
import static com.adplatform.restApi.domain.statistics.domain.report.QReportConversionDaily.reportConversionDaily;
import static com.adplatform.restApi.domain.statistics.domain.report.QReportDaily.reportDaily;
import static com.querydsl.jpa.JPAExpressions.select;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class CreativeQuerydslRepositoryImpl implements CreativeQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<CreativeDto.Response.Default> search(AdvertiserSearchRequest request, Pageable pageable) {
        List<CreativeDto.Response.Default> content = this.query.select(new QCreativeDto_Response_Default(
                        creative.id,
                        creative.name,
                        creative.config,
                        creative.systemConfig,
                        creative.reviewStatus,
                        creative.status,
                        campaign.id,
                        creative.adGroup.id,
                        adGroup.name,
                        creativeFile.id,
                        creativeFile.information.filename,
                        creativeFile.information.fileType,
                        new QReportInformationResponse(
                                this.getReportSubQuery(reportDaily.information.cost, request, "cost"),
                                this.getReportSubQuery(reportDaily.information.impression, request, "impression"),
                                this.getReportSubQuery(reportDaily.information.click, request, "click"),
                                this.getReportSubQuery(reportDaily.information.reach, request, "reach"),
                                this.getReportSubQuery(reportDaily.information.videoAutoPlay, request, "videoAutoPlay"),
                                this.getReportSubQuery(reportDaily.information.videoTouches, request, "videoTouches"),
                                this.getReportSubQuery(reportDaily.information.videoUnmute, request, "videoUnmute"),
                                this.getReportSubQuery(reportDaily.information.videoPlay3Seconds, request, "videoPlay3Seconds"),
                                this.getReportSubQuery(reportDaily.information.videoPlay5Seconds, request, "videoPlay5Seconds"),
                                this.getReportSubQuery(reportDaily.information.videoPlay10Seconds, request, "videoPlay10Seconds"),
                                this.getReportSubQuery(reportDaily.information.videoPlay15Seconds, request, "videoPlay15Seconds"),
                                this.getReportSubQuery(reportDaily.information.videoPlay30Seconds, request, "videoPlay30Seconds"),
                                this.getReportSubQuery(reportDaily.information.videoPlay60Seconds, request, "videoPlay60Seconds"),
                                this.getReportSubQuery(reportDaily.information.videoPlay25Percent, request, "videoPlay25Percent"),
                                this.getReportSubQuery(reportDaily.information.videoPlay50Percent, request, "videoPlay50Percent"),
                                this.getReportSubQuery(reportDaily.information.videoPlay75Percent, request, "videoPlay75Percent"),
                                this.getReportSubQuery(reportDaily.information.videoPlay100Percent, request, "videoPlay100Percent")
                        ),
                        new QReportConversionInformationResponse(
                                this.getReportConversionSubQuery(reportConversionDaily.information.signUpDay1, request),
                                this.getReportConversionSubQuery(reportConversionDaily.information.signUpDay7, request),
                                this.getReportConversionSubQuery(reportConversionDaily.information.purchaseDay1, request),
                                this.getReportConversionSubQuery(reportConversionDaily.information.purchaseDay7, request),
                                this.getReportConversionSubQuery(reportConversionDaily.information.viewCartDay1, request),
                                this.getReportConversionSubQuery(reportConversionDaily.information.viewCartDay7, request)
                        )
                ))
                .from(creative)
                .join(creative.adGroup, adGroup).on(
                        AdGroupCondition.inConfigElseWithOutStatusDel(request.getAdGroupConfigs()),
                        AdGroupCondition.inStatusElseWithOutStatusDel(request.getAdGroupStatuses())
                )
                .join(creative.files, creativeFile)
                .join(adGroup.campaign, campaign).on(
                        CampaignCondition.inConfigElseWithOutStatusDel(request.getCampaignConfigs()),
                        CampaignCondition.inStatusElseWithOutStatusDel(request.getCampaignStatuses())
                )
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        CampaignCondition.inId(request.getCampaignIds()),
                        CampaignCondition.containsName(request.getCampaignName()),
                        AdGroupCondition.inId(request.getAdGroupIds()),
                        AdGroupCondition.containsName(request.getAdGroupName()),
                        CreativeCondition.inId(request.getCreativeIds()),
                        CreativeCondition.containsName(request.getCreativeName()),
                        CreativeCondition.inFormat(request.getCreativeFormats()),
                        CreativeCondition.inConfigElseWithOutStatusDel(request.getCreativeConfigs()),
                        CreativeCondition.inStatusElseWithOutStatusDel(request.getCreativeStatuses()),
                        CreativeCondition.inReviewStatus(request.getCreativeReviewStatuses()),
                        AdTypeCondition.inName(request.getAdTypeNames()),
                        AdGoalCondition.inName(request.getAdGoalNames())
                )
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Creative.class, "creative", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(creative.countDistinct())
                .from(creative)
                .join(creative.adGroup, adGroup).on(
                        AdGroupCondition.inConfigElseWithOutStatusDel(request.getAdGroupConfigs()),
                        AdGroupCondition.inStatusElseWithOutStatusDel(request.getAdGroupStatuses())
                )
                .join(creative.files, creativeFile)
                .join(adGroup.campaign, campaign).on(
                        CampaignCondition.inConfigElseWithOutStatusDel(request.getCampaignConfigs()),
                        CampaignCondition.inStatusElseWithOutStatusDel(request.getCampaignStatuses())
                )
                .join(campaign.adTypeAndGoal, adTypeAndGoal)
                .join(adTypeAndGoal.adType, adType)
                .join(adTypeAndGoal.adGoal, adGoal)
                .where(
                        this.eqAdAccountId(request.getAdAccountId()),
                        CampaignCondition.inId(request.getCampaignIds()),
                        CampaignCondition.containsName(request.getCampaignName()),
                        AdGroupCondition.inId(request.getAdGroupIds()),
                        AdGroupCondition.containsName(request.getAdGroupName()),
                        CreativeCondition.inId(request.getCreativeIds()),
                        CreativeCondition.containsName(request.getCreativeName()),
                        CreativeCondition.inFormat(request.getCreativeFormats()),
                        CreativeCondition.inConfigElseWithOutStatusDel(request.getCreativeConfigs()),
                        CreativeCondition.inStatusElseWithOutStatusDel(request.getCreativeStatuses()),
                        CreativeCondition.inReviewStatus(request.getCreativeReviewStatuses()),
                        AdTypeCondition.inName(request.getAdTypeNames()),
                        AdGoalCondition.inName(request.getAdGoalNames())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Expression<Integer> getReportSubQuery(NumberPath<Integer> path, AdvertiserSearchRequest request, String alias) {
        return ExpressionUtils.as(select(path.sum())
                        .from(reportDaily)
                        .where(creative.id.eq(reportDaily.creativeId),
                                reportDaily.reportDate.between(request.getReportStartDate(), request.getReportEndDate())),
                alias);
    }

    private JPQLQuery<Integer> getReportConversionSubQuery(NumberPath<Integer> path, AdvertiserSearchRequest request) {
        return select(path.sum())
                .from(reportConversionDaily)
                .where(creative.id.eq(reportConversionDaily.creativeId),
                        reportConversionDaily.reportDate.between(request.getReportStartDate(), request.getReportEndDate()));
    }

    @Override
    public Optional<Creative> findDetailById(Integer id) {
        return Optional.ofNullable(this.query.selectFrom(creative)
                .leftJoin(creative.files, creativeFile).fetchJoin()
                .leftJoin(creative.opinionProofFiles, creativeOpinionProofFile)
                .where(creative.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression eqAdAccountId(Integer adAccountId) {
        return Objects.nonNull(adAccountId) ? campaign.adAccount.id.eq(adAccountId) : null;
    }
}
