package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.QAdAccountDto_Response_Page;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletSpend;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import static com.adplatform.restApi.domain.wallet.domain.QCash.cash;
import static com.adplatform.restApi.domain.wallet.domain.QWalletCashTotal.walletCashTotal;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AdAccountQuerydslRepositoryImpl implements AdAccountQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<AdAccountDto.Response.Page> search(Pageable pageable, AdAccountDto.Request.MySearch request, Integer userId) {
        List<AdAccountDto.Response.Page> content = this.query
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccount.walletMaster, walletMaster)
                .leftJoin(walletMaster.cashTotals, walletCashTotal)
                .leftJoin(walletCashTotal.cash, cash)
                .where(
                        adAccountUser.user.id.eq(userId),
                        cash.saleAffect.eq(true),
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                )
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .transform(GroupBy.groupBy(adAccount.id)
                        .list(new QAdAccountDto_Response_Page(
                                adAccount.id,
                                adAccount.name,
                                new QWalletDto_Response_WalletSpend(walletCashTotal.amount.sum()),
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

    private BooleanExpression eqId(Integer id) {
        return id != null ? adAccount.id.eq(id) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? adAccount.name.contains(name) : null;
    }
}
