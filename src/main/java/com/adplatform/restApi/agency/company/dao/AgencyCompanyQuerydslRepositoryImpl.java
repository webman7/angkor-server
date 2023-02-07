package com.adplatform.restApi.agency.company.dao;

import com.adplatform.restApi.adaccount.domain.AdAccount;
import com.adplatform.restApi.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.agency.company.dto.AgencyCompanyDto;
import com.adplatform.restApi.agency.company.dto.QAgencyCompanyDto_Response_SearchForAdmin;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.adplatform.restApi.wallet.dto.QWalletDto_Response_WalletSpend;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.agency.businessright.domain.QBusinessRight.businessRight;
import static com.adplatform.restApi.company.domain.QCompany.company;
import static com.adplatform.restApi.statistics.domain.report.QReportAdGroupDaily.reportAdGroupDaily;
import static com.adplatform.restApi.statistics.domain.sale.QSaleAmountDaily.saleAmountDaily;
import static com.adplatform.restApi.user.domain.QUser.user;
import static com.adplatform.restApi.wallet.domain.QCash.cash;
import static com.adplatform.restApi.wallet.domain.QWalletCashTotal.walletCashTotal;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
@Repository
public class AgencyCompanyQuerydslRepositoryImpl implements AgencyCompanyQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<AgencyCompanyDto.Response.SearchForAdmin> searchForAdmin(Pageable pageable, AgencyCompanyDto.Request.Search request, Integer companyId) {
        List<AgencyCompanyDto.Response.SearchForAdmin> content = this.getSearchForAdminQuery(pageable, request, companyId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
                .from(adAccount)
                .join(businessRight).on(
                        adAccount.id.eq(businessRight.adAccountId),
                        businessRight.startDate.loe(request.getCurrDate()),
                        businessRight.endDate.goe(request.getCurrDate())
                )
                .join(adAccount.company, company)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        adAccount.company.id.eq(companyId),
                        this.eqPlatformType(request.getPlatformType()),
                        this.searchKeyword(request.getSearchType(), request.getSearchKeyword())
                ).groupBy(adAccount.id);
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public List<AgencyCompanyDto.Response.SearchForAdmin> searchForAdmin(AgencyCompanyDto.Request.Search request, Integer companyId) {
        return this.getSearchForAdminQuery(null, request, companyId).fetch();
    }

    private JPAQuery<AgencyCompanyDto.Response.SearchForAdmin> getSearchForAdminQuery(
            Pageable pageable,
            AgencyCompanyDto.Request.Search request, Integer companyId) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        JPAQuery<AgencyCompanyDto.Response.SearchForAdmin> query = this.query.select(
                        new QAgencyCompanyDto_Response_SearchForAdmin(
                                adAccount.id,
                                adAccount.name,
                                as(select(user.name)
                                                .from(user)
                                                .join(adAccountUser).on(user.id.eq(adAccountUser.id.userId),
                                                        adAccountUser.memberType.eq(AdAccountUser.MemberType.MASTER))
                                                .where(adAccountUser.id.adAccountId.eq(adAccount.id)),
                                        "marketerName"),
                                company.type,
                                new QWalletDto_Response_WalletSpend(
                                        as(select(walletCashTotal.availableAmount.sum())
                                                        .from(walletCashTotal)
                                                        .join(walletCashTotal.cash, cash).on(cash.saleAffect.eq(true))
                                                        .where(walletCashTotal.id.walletMasterId.eq(adAccount.id)),
                                                "cash"),
                                        as(select(reportAdGroupDaily.information.cost.sum())
                                                        .from(reportAdGroupDaily)
                                                        .where(reportAdGroupDaily.adAccountId.eq(adAccount.id),
                                                                reportAdGroupDaily.reportDate.eq(Integer.valueOf(now.format(yyyyMMdd)))),
                                                "todaySpend"),
                                        as(select(saleAmountDaily.saleAmount)
                                                        .from(saleAmountDaily)
                                                        .where(saleAmountDaily.id.adAccountId.eq(adAccount.id),
                                                                saleAmountDaily.id.statDate.eq(Integer.valueOf(now.minusDays(1L).format(yyyyMMdd)))),
                                                "yesterdaySpend"),
                                        as(select(saleAmountDaily.saleAmount.sum())
                                                        .from(saleAmountDaily)
                                                        .where(
                                                                saleAmountDaily.id.adAccountId.eq(adAccount.id),
                                                                saleAmountDaily.id.statDate.between(
                                                                        Integer.valueOf(now.withDayOfMonth(1).format(yyyyMMdd)),
                                                                        Integer.valueOf(now.withDayOfMonth(now.lengthOfMonth()).format(yyyyMMdd))
                                                                )
                                                        ).groupBy(saleAmountDaily.id.adAccountId),
                                                "monthSpendQuery")
                                ),
                                adAccount.creditLimit,
                                adAccount.preDeferredPayment,
                                adAccount.config,
                                adAccount.adminStop,
                                adAccount.outOfBalance
                        )
                )
                .from(adAccount)
                .join(businessRight).on(
                        adAccount.id.eq(businessRight.adAccountId),
                        businessRight.startDate.loe(request.getCurrDate()),
                        businessRight.endDate.goe(request.getCurrDate())
                )
                .join(adAccount.company, company)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        adAccount.company.id.eq(companyId),
                        this.eqPlatformType(request.getPlatformType()),
                        this.searchKeyword(request.getSearchType(), request.getSearchKeyword())
                )
                .groupBy(
                        adAccount.id
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    private BooleanExpression eqPlatformType(String platformType) {
        return StringUtils.hasText(platformType) ? adAccount.platformType.eq(AdAccount.PlatformType.valueOf(platformType)) : null;
    }

    private BooleanExpression searchKeyword(String searchType, String searchKeyword) {
        return StringUtils.hasText(searchKeyword) ? (searchType.equals("AD_ACCOUNT_ID") ? adAccount.id.eq(Integer.parseInt(searchKeyword)) : adAccount.name.contains(searchKeyword)) : null;
    }}
