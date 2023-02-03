package com.adplatform.restApi.agency.adaccount.dao;

import com.adplatform.restApi.advertiser.adaccount.domain.AdAccount;
import com.adplatform.restApi.agency.adaccount.dto.AgencyAdAccountDto;
import com.adplatform.restApi.agency.businessright.domain.BusinessRightRequest;
import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import com.adplatform.restApi.agency.businessright.dto.QBusinessRightDto_Response_Search;
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

import static com.adplatform.restApi.advertiser.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.advertiser.user.domain.QUser.user;
import static com.adplatform.restApi.agency.businessright.domain.QBusinessRight.businessRight;
import static com.querydsl.core.types.ExpressionUtils.as;

@RequiredArgsConstructor
@Repository
public class AgencyAdAccountQuerydslRepositoryImpl implements AgencyAdAccountQuerydslRepository {
//
//    private final JPAQueryFactory query;
//
//    @Override
//    public Page<AgencyAdAccountDto.Response.Search> search(Pageable pageable, AgencyAdAccountDto.Request.Search request) {
//        List<AgencyAdAccountDto.Response.Search> content = this.getSearchQuery(pageable, request)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
//                .from(adAccount, businessRightRequest, user)
//                .where(
//                        adAccount.id.eq(businessRightRequest.adAccountId),
//                        this.eqPlatformType(request.getPlatformType()),
//                        this.eqStatus(request.getBusinessRightStatus()),
//                        businessRightRequest.createdUserNo.eq(user.id),
//                        businessRightRequest.requestCompanyId.eq(request.getCompanyId())
//                );
//        // group by 일 경우 () -> countQuery.fetch().size()
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//    }
//
//    private JPAQuery<AgencyAdAccountDto.Response.Search> getSearchQuery(
//            Pageable pageable,
//            AgencyAdAccountDto.Request.Search request) {
//
//        JPAQuery<AgencyAdAccountDto.Response.Search> query = this.query.select(
//                        new QBusinessRightDto_Response_Search(
//                                adAccount.id,
//                                adAccount.name,
//                                as(user.loginId, "marketerId"),
//                                as(user.name, "marketerName"),
//                                adAccount.createdAt
//                        )
//                )
//                .from(adAccount)
//                .leftJoin(businessRight).on(adAccount.id.eq(businessRight.adAccountId),
//                        businessRight.startDate)
//                .where(
//                        adAccount.id.eq(businessRightRequest.adAccountId),
//                        this.eqPlatformType(request.getPlatformType()),
//                        this.eqStatus(request.getBusinessRightStatus()),
//                        businessRightRequest.createdUserNo.eq(user.id),
//                        businessRightRequest.requestCompanyId.eq(request.getCompanyId())
//                );
//
//        return Objects.nonNull(pageable)
//                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "search", pageable.getSort()).toArray(OrderSpecifier[]::new))
//                : query;
//    }
//
//    private BooleanExpression eqPlatformType(String platformType) {
//        try {
//            return platformType != null && !platformType.isEmpty() ? adAccount.platformType.eq(AdAccount.PlatformType.valueOf(platformType)) : null;
//        } catch (java.lang.IllegalArgumentException e) {
//            return null;
//        }
//    }
//
//    private BooleanExpression eqStatus(String status) {
//        try {
//            return status != null && !status.isEmpty() ? businessRightRequest.status.eq(BusinessRightRequest.Status.valueOf(status)) : null;
//        } catch (java.lang.IllegalArgumentException e) {
//            return null;
//        }
//    }
}
