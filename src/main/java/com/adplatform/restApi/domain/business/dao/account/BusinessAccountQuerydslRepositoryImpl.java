package com.adplatform.restApi.domain.business.dao.account;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.dto.account.*;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.dashboard.dto.DashboardDto;
import com.adplatform.restApi.domain.company.dto.QCompanyDto_Response_CompanyInfo;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_BaseInfo;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletBalance;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletSpend;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccount.businessAccount;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccountUser.businessAccountUser;
import static com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto.Request;
import static com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto.Response;
import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.advertiser.campaign.domain.QCampaign.campaign;
import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreative.creative;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.statistics.domain.report.QReportAdGroupDaily.reportAdGroupDaily;
import static com.adplatform.restApi.domain.statistics.domain.sale.QSaleAdAccountDaily.saleAdAccountDaily;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;

/**
 * @author junny
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class BusinessAccountQuerydslRepositoryImpl implements BusinessAccountQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Response.ForAdminSearch> searchForAdmin(Pageable pageable, Request.ForAdminSearch request) {
        List<Response.ForAdminSearch> content = this.getSearchForAdminQuery(pageable, request)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(businessAccount.count())
                .from(businessAccount)
                .join(businessAccount.company, company)
                .join(businessAccount.businessAccountUsers, businessAccountUser)
                .where(
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                ).groupBy(businessAccount.id);
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public List<Response.ForAdminSearch> searchForAdmin(Request.ForAdminSearch request) {
        return this.getSearchForAdminQuery(null, request).fetch();
    }

    private JPAQuery<Response.ForAdminSearch> getSearchForAdminQuery(
            Pageable pageable,
            Request.ForAdminSearch request) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        JPAQuery<Response.ForAdminSearch> query = this.query.select(
                        new QBusinessAccountDto_Response_ForAdminSearch(
                                businessAccount.id,
                                businessAccount.name,
                                as(select(user.name)
                                                .from(user)
                                                .join(businessAccountUser).on(user.id.eq(businessAccountUser.id.userId),
                                                        businessAccountUser.memberType.eq(BusinessAccountUser.MemberType.MASTER))
                                                .where(businessAccountUser.id.businessAccountId.eq(businessAccount.id)),
                                        "marketerName"),
                                new QWalletDto_Response_WalletSpend(
                                        as(select(walletMaster.availableAmount.sum())
                                                        .from(walletMaster)
                                                        .where(walletMaster.id.eq(businessAccount.id)),
                                                "cash"),
                                        as(select(reportAdGroupDaily.information.cost.sum())
                                                        .from(reportAdGroupDaily)
                                                        .where(reportAdGroupDaily.businessAccountId.eq(businessAccount.id),
                                                                reportAdGroupDaily.reportDate.eq(Integer.valueOf(now.format(yyyyMMdd)))),
                                                "todaySpend"),
                                        as(select(saleAdAccountDaily.supplyAmount.sum())
                                                        .from(saleAdAccountDaily)
                                                        .where(saleAdAccountDaily.id.businessAccountId.eq(businessAccount.id),
                                                                saleAdAccountDaily.id.statDate.eq(Integer.valueOf(now.minusDays(1L).format(yyyyMMdd)))),
                                                "yesterdaySpend"),
                                        as(select(saleAdAccountDaily.supplyAmount.sum())
                                                        .from(saleAdAccountDaily)
                                                        .where(
                                                                saleAdAccountDaily.id.businessAccountId.eq(businessAccount.id),
                                                                saleAdAccountDaily.id.statDate.between(
                                                                        Integer.valueOf(now.withDayOfMonth(1).format(yyyyMMdd)),
                                                                        Integer.valueOf(now.withDayOfMonth(now.lengthOfMonth()).format(yyyyMMdd))
                                                                )
                                                        ).groupBy(saleAdAccountDaily.id.businessAccountId),
                                                "monthSpendQuery")
                                ),
                                businessAccount.creditLimit,
                                businessAccount.prePayment,
                                businessAccount.config
                        )
                )
                .from(businessAccount)
                .join(businessAccount.company, company)
                .join(businessAccount.businessAccountUsers, businessAccountUser)
                .where(
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                )
                .groupBy(
                        businessAccount.id
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(BusinessAccount.class, "businessAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Page<Response.ForAgencySearch> searchForAgency(Pageable pageable, Request.ForAgencySearch request, Integer userId) {
        List<Response.ForAgencySearch> content = this.getSearchForAgencyQuery(pageable, request, userId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(businessAccount.count())
                .from(businessAccount)
                .join(businessAccount.company, company)
                .join(businessAccount.businessAccountUsers, businessAccountUser)
                .where(
                        businessAccountUser.id.userId.eq(userId),
                        this.eqId(request.getId()),
                        this.containsName(request.getName()),
                        this.eqPrePayment(request.getPrePayment())
                ).groupBy(businessAccount.id);
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public List<Response.ForAgencySearch> searchForAgency(Request.ForAgencySearch request, Integer userId) {
        return this.getSearchForAgencyQuery(null, request, userId).fetch();
    }

    private JPAQuery<Response.ForAgencySearch> getSearchForAgencyQuery(
            Pageable pageable,
            Request.ForAgencySearch request,
            Integer userId) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        JPAQuery<Response.ForAgencySearch> query = this.query.select(
                        new QBusinessAccountDto_Response_ForAgencySearch(
                                businessAccount.id,
                                businessAccount.name,
                                as(select(user.name)
                                                .from(user)
                                                .join(businessAccountUser).on(user.id.eq(businessAccountUser.id.userId),
                                                        businessAccountUser.memberType.eq(BusinessAccountUser.MemberType.MASTER))
                                                .where(businessAccountUser.id.businessAccountId.eq(businessAccount.id)),
                                        "marketerName"),
                                new QWalletDto_Response_WalletSpend(
                                        as(select(walletMaster.availableAmount.sum())
                                                        .from(walletMaster)
                                                        .where(walletMaster.id.eq(businessAccount.id)),
                                                "cash"),
                                        as(select(reportAdGroupDaily.information.cost.sum())
                                                        .from(reportAdGroupDaily)
                                                        .where(reportAdGroupDaily.businessAccountId.eq(businessAccount.id),
                                                                reportAdGroupDaily.reportDate.eq(Integer.valueOf(now.format(yyyyMMdd)))),
                                                "todaySpend"),
                                        as(select(saleAdAccountDaily.supplyAmount.sum())
                                                        .from(saleAdAccountDaily)
                                                        .where(saleAdAccountDaily.id.businessAccountId.eq(businessAccount.id),
                                                                saleAdAccountDaily.id.statDate.eq(Integer.valueOf(now.minusDays(1L).format(yyyyMMdd)))),
                                                "yesterdaySpend"),
                                        as(select(saleAdAccountDaily.supplyAmount.sum())
                                                        .from(saleAdAccountDaily)
                                                        .where(
                                                                saleAdAccountDaily.id.businessAccountId.eq(businessAccount.id),
                                                                saleAdAccountDaily.id.statDate.between(
                                                                        Integer.valueOf(now.withDayOfMonth(1).format(yyyyMMdd)),
                                                                        Integer.valueOf(now.withDayOfMonth(now.lengthOfMonth()).format(yyyyMMdd))
                                                                )
                                                        ).groupBy(saleAdAccountDaily.id.businessAccountId),
                                                "monthSpendQuery")
                                ),
                                businessAccount.creditLimit,
                                businessAccount.prePayment,
                                businessAccount.config
                        )
                )
                .from(businessAccount)
                .join(businessAccount.company, company)
                .join(businessAccount.businessAccountUsers, businessAccountUser)
                .where(
                        businessAccountUser.id.userId.eq(userId),
                        this.eqId(request.getId()),
                        this.containsName(request.getName()),
                        this.eqPrePayment(request.getPrePayment())
                )
                .groupBy(
                        businessAccount.id
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(BusinessAccount.class, "businessAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Page<Response.ForAdvertiserSearch> searchForAdvertiser(
            Pageable pageable, Integer id, String name, Integer loginUserNo, BusinessAccountUser.Status status) {
        List<Response.ForAdvertiserSearch> content = this.getSearchForAdvertiserQuery(pageable, id, name, loginUserNo, status)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(Wildcard.count)
                .from(businessAccount)
                .join(businessAccount.businessAccountUsers, businessAccountUser)
                .join(businessAccountUser.user, user)
                .where(
                        businessAccountUser.id.userId.eq(loginUserNo),
                        businessAccountUser.status.eq(status),
                        this.eqId(id),
                        this.containsName(name));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Response.ForAdvertiserSearch> searchForAdvertiser(
            Integer id, String name, Integer loginUserNo, BusinessAccountUser.Status status) {
        return this.getSearchForAdvertiserQuery(null, id, name, loginUserNo, status).fetch();
    }

    private JPAQuery<Response.ForAdvertiserSearch> getSearchForAdvertiserQuery(
            Pageable pageable,
            Integer id, String name, Integer loginUserNo, BusinessAccountUser.Status status) {

        JPAQuery<Response.ForAdvertiserSearch> query = this.query.select(new QBusinessAccountDto_Response_ForAdvertiserSearch(
                        businessAccount.id,
                        businessAccount.name,
                        as(select(user.loginId)
                                        .from(user)
                                        .where(user.id.in(select(businessAccountUser.id.userId)
                                                .from(businessAccountUser)
                                                .where(businessAccountUser.id.businessAccountId.eq(businessAccount.id),
                                                        businessAccountUser.memberType.eq(BusinessAccountUser.MemberType.MASTER)))),
                                "masterEmail"),
                        as(select(Wildcard.count)
                                        .from(businessAccountUser)
                                        .where(businessAccountUser.id.businessAccountId.eq(businessAccount.id),
                                                businessAccountUser.status.eq(status)),
                                "memberSize"),
                        businessAccount.config,
                        businessAccountUser.status))
                .from(businessAccount)
                .join(businessAccount.businessAccountUsers, businessAccountUser)
                .join(businessAccountUser.user, user)
                .where(
                        businessAccountUser.id.userId.eq(loginUserNo),
                        businessAccountUser.status.eq(status),
                        this.eqId(id),
                        this.containsName(name));

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(BusinessAccount.class, "businessAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Page<Response.ForCashSearch> searchForCash(Pageable pageable, Request.ForCashSearch request) {
        List<Response.ForCashSearch> content = this.getSearchForCashQuery(pageable, request)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(businessAccount.count())
                .from(businessAccount)
                .join(businessAccount.company, company)
                .join(businessAccount.businessAccountUsers, businessAccountUser)
                .where(
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                ).groupBy(businessAccount.id);
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public List<Response.ForCashSearch> searchForCash(Request.ForCashSearch request) {
        return this.getSearchForCashQuery(null, request).fetch();
    }

    private JPAQuery<Response.ForCashSearch> getSearchForCashQuery(
            Pageable pageable,
            Request.ForCashSearch request) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        JPAQuery<Response.ForCashSearch> query = this.query.select(
                        new QBusinessAccountDto_Response_ForCashSearch(
                                businessAccount.id,
                                businessAccount.name,
                                as(select(user.name)
                                                .from(user)
                                                .join(businessAccountUser).on(user.id.eq(businessAccountUser.id.userId),
                                                        businessAccountUser.memberType.eq(BusinessAccountUser.MemberType.MASTER))
                                                .where(businessAccountUser.id.businessAccountId.eq(businessAccount.id)),
                                        "marketerName"),
                                new QWalletDto_Response_WalletBalance(
                                        as(select(walletMaster.availableAmount.sum())
                                                        .from(walletMaster)
                                                        .where(walletMaster.id.eq(businessAccount.id)),
                                                "cash")
                                ),
                                businessAccount.creditLimit,
                                businessAccount.prePayment,
                                businessAccount.config
                        )
                )
                .from(businessAccount)
                .join(businessAccount.company, company)
                .join(businessAccount.businessAccountUsers, businessAccountUser)
                .where(
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                )
                .groupBy(
                        businessAccount.id
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(BusinessAccount.class, "businessAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Optional<Response.BusinessAccountCount> countStatusYN(Integer loginUserNo) {
        return Optional.ofNullable(this.query.select(new QBusinessAccountDto_Response_BusinessAccountCount(
                        as(select(businessAccountUser.id.userId.count())
                                        .from(businessAccountUser)
                                        .where(businessAccountUser.id.userId.eq(businessAccountUser.id.userId), businessAccountUser.status.eq(BusinessAccountUser.Status.Y)),
                                "statusYCount"),
                        as(select(businessAccountUser.id.userId.count())
                                        .from(businessAccountUser)
                                        .where(businessAccountUser.id.userId.eq(businessAccountUser.id.userId), businessAccountUser.status.eq(BusinessAccountUser.Status.N)),
                                "statusNCount")
                ))
                .from(businessAccountUser)
                .where(businessAccountUser.id.userId.eq(loginUserNo))
                .groupBy(businessAccountUser.id.userId)
                .fetchOne());
    }

    @Override
    public BusinessAccountDto.Response.BusinessAccountInfo businessAccountInfo(Integer businessAccountId) {
        return this.query.select(new QBusinessAccountDto_Response_BusinessAccountInfo(
                        businessAccount.id,
                        businessAccount.name,
                        businessAccount.config,
                        new QCompanyDto_Response_CompanyInfo(
                            company.id,
                            company.name,
                            company.type,
                            company.registrationNumber,
                            company.representationName,
                            company.address,
                            company.businessCategory,
                            company.businessItem,
                            company.taxBillEmail,
                            company.bank,
                            company.accountNumber,
                            company.accountOwner
                        )
                ))
                .from(businessAccount, company)
                .where(businessAccount.id.eq(businessAccountId),
                        businessAccount.company.id.eq(company.id)
                )
                .fetchOne();
    }

    @Override
    public BusinessAccountDto.Response.BusinessAccountCashInfo businessAccountCashInfo(Integer businessAccountId) {
        return this.query.select(new QBusinessAccountDto_Response_BusinessAccountCashInfo(
                        walletMaster.availableAmount.sum(),
                        walletMaster.totalReserveAmount.sum()
                ))
                .from(businessAccount, walletMaster)
                .where(businessAccount.id.eq(businessAccountId),
                       businessAccount.id.eq(walletMaster.id)
                ).fetchOne();
    }

    @Override
    public List<BusinessAccountDto.Response.AdAccountInfo> businessAccountByAdAccounts(Integer businessAccountId) {
        return this.query.select(new QBusinessAccountDto_Response_AdAccountInfo(
                    adAccount.id,
                    adAccount.name
                ))
                .from(businessAccount, adAccount)
                .where(businessAccount.id.eq(businessAccountId),
                       adAccount.businessAccount.id.eq(businessAccount.id),
                       adAccount.config.in(AdAccount.Config.ON, AdAccount.Config.OFF)
                )
                .fetch();
    }

    @Override
    public List<BusinessAccountDto.Response.AdAccountMemberInfo> businessAccountByAdAccountsMember(Integer businessAccountId, Integer loginUserNo) {
        return this.query.select(new QBusinessAccountDto_Response_AdAccountMemberInfo(
                        adAccount.id,
                        adAccount.name,
                        adAccountUser.memberType
                ))
                .from(businessAccount, adAccount, adAccountUser)
                .where(businessAccount.id.eq(businessAccountId),
                        adAccount.businessAccount.id.eq(businessAccount.id),
                        adAccount.id.eq(adAccountUser.id.adAccountId),
                        adAccountUser.user.id.eq(loginUserNo),
                        businessAccount.config.in(BusinessAccount.Config.ON, BusinessAccount.Config.OFF),
                        adAccount.config.in(AdAccount.Config.ON, AdAccount.Config.OFF),
                        adAccountUser.status.eq(AdAccountUser.Status.Y)
                )
                .fetch();
    }

    private BooleanExpression eqId(Integer id) {
        return id != null ? businessAccount.id.eq(id) : null;
    }

    private BooleanExpression eqPrePayment(Boolean prePayment) {
        return prePayment != null ? businessAccount.prePayment.eq(prePayment) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? businessAccount.name.contains(name) : null;
    }
}
