package com.adplatform.restApi.advertiser.adgroup.dao.adgroup;

import com.adplatform.restApi.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.advertiser.adgroup.dto.adgroup.QAdGroupDto_Response_Budget;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.adplatform.restApi.advertiser.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.advertiser.adgroup.domain.QAdGroupDemographicTarget.adGroupDemographicTarget;
import static com.adplatform.restApi.advertiser.adgroup.domain.QAdGroupSchedule.adGroupSchedule;
import static com.adplatform.restApi.advertiser.adgroup.domain.QDevice.device;
import static com.adplatform.restApi.advertiser.adgroup.domain.QMedia.media;
import static com.adplatform.restApi.advertiser.campaign.domain.QCampaign.campaign;
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

    @Override
    public List<AdGroupDto.Response.Budget> getBudget(Integer id) {
        return this.query.select(new QAdGroupDto_Response_Budget(adGroup.id, adGroup.budgetAmount))
                .from(adGroup)
                .where(adGroup.campaign.id.eq(id))
                .fetch();
    }

    private BooleanExpression eqAdAccountId(Integer adAccountId) {
        return nonNull(adAccountId) ? adGroup.campaign.adAccount.id.eq(adAccountId) : null;
    }
}
