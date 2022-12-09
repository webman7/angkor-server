package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.*;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletSpend;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto.Request;
import static com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto.Response;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.statistics.domain.sale.QSaleAmountDaily.saleAmountDaily;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.adplatform.restApi.domain.wallet.domain.QCash.cash;
import static com.adplatform.restApi.domain.wallet.domain.QWalletCashTotal.walletCashTotal;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class AdAccountQuerydslRepositoryImpl implements AdAccountQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Response.ForAgencySearch> searchForAgency(Pageable pageable, Request.ForAgencySearch request, Integer userId) {
        List<Response.ForAgencySearch> content = this.getSearchForAgencyQuery(pageable, request, userId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
                .from(adAccount)
                .join(adAccount.company, company)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        adAccountUser.id.userId.eq(userId),
                        this.eqId(request.getId()),
                        this.containsName(request.getName()),
                        this.eqPreDeferredPayment(request.getPreDeferredPayment())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
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
                        new QAdAccountDto_Response_ForAgencySearch(
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
                                        as(select(walletCashTotal.amount.sum())
                                                        .from(walletCashTotal)
                                                        .join(walletCashTotal.cash, cash).on(cash.saleAffect.eq(true))
                                                        .where(walletCashTotal.id.walletMasterId.eq(adAccount.id)),
                                                "cash"),
                                        as(select(saleAmountDaily.saleAmount)
                                                        .from(saleAmountDaily)
                                                        .where(saleAmountDaily.id.adAccountId.eq(adAccount.id),
                                                                saleAmountDaily.id.statDate.eq(Integer.valueOf(now.format(yyyyMMdd)))),
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
                .join(adAccount.company, company)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        adAccountUser.id.userId.eq(userId),
                        this.eqId(request.getId()),
                        this.containsName(request.getName()),
                        this.eqPreDeferredPayment(request.getPreDeferredPayment())
                )
                .groupBy(
                        adAccount.id,
                        adAccount.name,
                        company.type,
                        adAccount.creditLimit,
                        adAccount.preDeferredPayment,
                        adAccount.company,
                        adAccount.adminStop,
                        adAccount.outOfBalance
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Page<Response.ForAdvertiserSearch> searchForAdvertiser(
            Pageable pageable, Integer id, String name, Integer loginUserId, AdAccountUser.RequestStatus requestStatus) {
        List<Response.ForAdvertiserSearch> content = this.getSearchForAdvertiserQuery(pageable, id, name, loginUserId, requestStatus)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(Wildcard.count)
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccountUser.user, user)
                .where(
                        adAccountUser.id.userId.eq(loginUserId),
                        adAccountUser.requestStatus.eq(requestStatus),
                        this.eqId(id),
                        this.containsName(name));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Response.ForAdvertiserSearch> searchForAdvertiser(
            Integer id, String name, Integer loginUserId, AdAccountUser.RequestStatus requestStatus) {
        return this.getSearchForAdvertiserQuery(null, id, name, loginUserId, requestStatus).fetch();
    }

    private JPAQuery<Response.ForAdvertiserSearch> getSearchForAdvertiserQuery(
            Pageable pageable,
            Integer id, String name, Integer loginUserId, AdAccountUser.RequestStatus requestStatus) {

        JPAQuery<Response.ForAdvertiserSearch> query = this.query.select(new QAdAccountDto_Response_ForAdvertiserSearch(
                        adAccount.id,
                        adAccount.name,
                        as(select(user.loginId)
                                        .from(user)
                                        .where(user.id.in(select(adAccountUser.id.userId)
                                                .from(adAccountUser)
                                                .where(adAccountUser.id.adAccountId.eq(adAccount.id),
                                                        adAccountUser.memberType.eq(AdAccountUser.MemberType.MASTER)))),
                                "masterEmail"),
                        as(select(Wildcard.count)
                                        .from(adAccountUser)
                                        .where(adAccountUser.id.adAccountId.eq(adAccount.id),
                                                adAccountUser.requestStatus.eq(requestStatus)),
                                "memberSize"),
                        adAccount.config,
                        adAccount.adminStop,
                        adAccount.outOfBalance,
                        adAccountUser.requestStatus))
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccountUser.user, user)
                .where(
                        adAccountUser.id.userId.eq(loginUserId),
                        adAccountUser.requestStatus.eq(requestStatus),
                        this.eqId(id),
                        this.containsName(name));

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Optional<Response.AdAccountCount> countRequestStatusYN(Integer loginUserId) {
        return Optional.ofNullable(this.query.select(new QAdAccountDto_Response_AdAccountCount(
                        as(select(adAccountUser.id.userId.count())
                                        .from(adAccountUser)
                                        .where(adAccountUser.id.userId.eq(adAccountUser.id.userId), adAccountUser.requestStatus.eq(AdAccountUser.RequestStatus.Y)),
                                "requestStatusYCount"),
                        as(select(adAccountUser.id.userId.count())
                                        .from(adAccountUser)
                                        .where(adAccountUser.id.userId.eq(adAccountUser.id.userId), adAccountUser.requestStatus.eq(AdAccountUser.RequestStatus.N)),
                                "requestStatusNCount")
                ))
                .from(adAccountUser)
                .where(adAccountUser.id.userId.eq(loginUserId))
                .groupBy(adAccountUser.id.userId)
                .fetchOne());
    }

    @Override
    public AdAccountDto.Response.AdAccountInfo adAccountInfo(Integer adAccountId) {
        return this.query.select(new QAdAccountDto_Response_AdAccountInfo(
                        adAccount.id,
                        adAccount.name,
                        adAccount.config,
                        adAccount.adminStop,
                        adAccount.outOfBalance
                ))
                .from(adAccount)
                .where(adAccount.id.eq(adAccountId))
                .fetchOne();
    }

    private BooleanExpression eqId(Integer id) {
        return id != null ? adAccount.id.eq(id) : null;
    }

    private BooleanExpression eqPreDeferredPayment(Boolean preDeferredPayment) {
        return preDeferredPayment != null ? adAccount.preDeferredPayment.eq(preDeferredPayment) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? adAccount.name.contains(name) : null;
    }
}
