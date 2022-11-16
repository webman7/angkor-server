package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.QAdAccountDto_Response_ForAdvertiserSearch;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.QAdAccountDto_Response_ForAgencySearch;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletSpend;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
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

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.statistics.domain.QSaleAmountDaily.saleAmountDaily;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.adplatform.restApi.domain.wallet.domain.QCash.cash;
import static com.adplatform.restApi.domain.wallet.domain.QWalletCashTotal.walletCashTotal;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;
import static com.querydsl.jpa.JPAExpressions.select;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AdAccountQuerydslRepositoryImpl implements AdAccountQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<AdAccountDto.Response.ForAgencySearch> searchForAgency(Pageable pageable, AdAccountDto.Request.ForAgencySearch request, Integer userId) {
        LocalDate now = LocalDate.now();
        Expression<Integer> todaySpendQuery = ExpressionUtils.as(select(saleAmountDaily.saleAmount)
                        .from(saleAmountDaily)
                        .where(saleAmountDaily.id.adAccountId.eq(adAccount.id),
                                saleAmountDaily.id.statDate.eq(Integer.valueOf(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")))))
                , ExpressionUtils.path(Integer.class, "todaySpend"));

        Expression<Integer> yesterdaySpendQuery = ExpressionUtils.as(select(saleAmountDaily.saleAmount)
                        .from(saleAmountDaily)
                        .where(saleAmountDaily.id.adAccountId.eq(adAccount.id),
                                saleAmountDaily.id.statDate.eq(Integer.valueOf(now.minusDays(1L).format(DateTimeFormatter.ofPattern("yyyyMMdd")))))
                , ExpressionUtils.path(Integer.class, "yesterdaySpend"));

        Expression<Integer> monthSpendQuery = ExpressionUtils.as(select(saleAmountDaily.saleAmount.sum())
                        .from(saleAmountDaily)
                        .where(
                                saleAmountDaily.id.adAccountId.eq(adAccount.id),
                                saleAmountDaily.id.statDate.between(
                                        Integer.valueOf(now.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))),
                                        Integer.valueOf(now.withDayOfMonth(now.lengthOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                                )
                        ).groupBy(saleAmountDaily.id.adAccountId)
                , ExpressionUtils.path(Integer.class, "monthSpendQuery"));

        List<AdAccountDto.Response.ForAgencySearch> content = this.query
                .select(
                        adAccount.id,
                        adAccount.name,
                        company.type,
                        adAccount.creditLimit,
                        adAccount.preDeferredPayment,
                        adAccount.config,
                        adAccount.adminStop,
                        adAccount.outOfBalance,
                        walletCashTotal.amount,
                        todaySpendQuery,
                        yesterdaySpendQuery,
                        monthSpendQuery
                )
                .from(adAccount)
                .join(adAccount.company, company)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccount.walletMaster, walletMaster)
                .leftJoin(walletMaster.cashTotals, walletCashTotal)
                .leftJoin(walletCashTotal.cash, cash).on(cash.saleAffect.eq(true))
                .where(
                        adAccountUser.id.userId.eq(userId),
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                )
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .transform(GroupBy.groupBy(adAccount.id)
                        .list(new QAdAccountDto_Response_ForAgencySearch(
                                adAccount.id,
                                adAccount.name,
                                company.type,
                                new QWalletDto_Response_WalletSpend(
                                        GroupBy.sum(walletCashTotal.amount),
                                        todaySpendQuery,
                                        yesterdaySpendQuery,
                                        monthSpendQuery
                                ),
                                adAccount.creditLimit,
                                adAccount.preDeferredPayment,
                                adAccount.config,
                                adAccount.adminStop,
                                adAccount.outOfBalance
                        )));

        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
                .from(adAccount)
                .join(adAccount.company, company)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccount.walletMaster, walletMaster)
                .leftJoin(walletMaster.cashTotals, walletCashTotal)
                .leftJoin(walletCashTotal.cash, cash).on(cash.saleAffect.eq(true))
                .where(
                        adAccountUser.id.userId.eq(userId),
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                )
                .from(adAccount);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<AdAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            Pageable pageable, Integer id, String name, Integer loginUserId, AdAccountUser.RequestStatus requestStatus) {
        List<AdAccountDto.Response.ForAdvertiserSearch> content = this.query.select(new QAdAccountDto_Response_ForAdvertiserSearch(
                        adAccount.id,
                        adAccount.name,
                        ExpressionUtils.as(select(user.loginId)
                                        .from(user)
                                        .where(user.id.in(select(adAccountUser.id.userId)
                                                .from(adAccountUser)
                                                .where(adAccountUser.id.adAccountId.eq(adAccount.id),
                                                        adAccountUser.memberType.eq(AdAccountUser.MemberType.MASTER)))),
                                "masterEmail"),
                        ExpressionUtils.as(select(Wildcard.count)
                                        .from(adAccountUser)
                                        .where(adAccountUser.id.adAccountId.eq(adAccount.id),
                                                adAccountUser.requestStatus.eq(requestStatus)),
                                "memberSize"),
                        adAccount.config,
                        adAccount.outOfBalance,
                        adAccountUser.requestStatus))
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccountUser.user, user)
                .where(
                        adAccountUser.id.userId.eq(loginUserId),
                        adAccountUser.requestStatus.eq(requestStatus),
                        this.eqId(id),
                        this.containsName(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(Wildcard.count)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccountUser.user, user)
                .where(
                        adAccountUser.id.userId.eq(loginUserId),
                        adAccountUser.requestStatus.eq(requestStatus),
                        this.eqId(id),
                        this.containsName(name));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqId(Integer id) {
        return id != null ? adAccount.id.eq(id) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? adAccount.name.contains(name) : null;
    }
}
