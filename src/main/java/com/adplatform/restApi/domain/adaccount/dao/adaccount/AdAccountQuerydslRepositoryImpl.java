package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.QAdAccountDto_Response_ForAdvertiserSearch;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.QAdAccountDto_Response_ForAgencySearch;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletSpend;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.group.GroupBy;
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

import java.util.List;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
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
        List<AdAccountDto.Response.ForAgencySearch> content = this.query
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccount.walletMaster, walletMaster)
                .leftJoin(walletMaster.cashTotals, walletCashTotal)
                .leftJoin(walletCashTotal.cash, cash)
                .where(
                        adAccountUser.id.userId.eq(userId),
                        cash.saleAffect.eq(true),
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
                                new QWalletDto_Response_WalletSpend(GroupBy.sum(walletCashTotal.amount)),
                                adAccount.creditLimit,
                                adAccount.preDeferredPayment,
                                adAccount.config,
                                adAccount.adminStop,
                                adAccount.outOfBalance
                        )));

        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
                .where(
                        adAccountUser.user.id.eq(userId),
                        cash.saleAffect.eq(true),
                        this.eqId(request.getId()),
                        this.containsName(request.getName()))
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
