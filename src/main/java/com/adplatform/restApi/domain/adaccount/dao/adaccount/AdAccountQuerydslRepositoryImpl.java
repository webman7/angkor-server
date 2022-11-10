package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.QAdAccountDto_Response_Page;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
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
import org.springframework.util.StringUtils;

import java.util.List;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.querydsl.core.group.GroupBy.groupBy;

@RequiredArgsConstructor
@Repository
public class AdAccountQuerydslRepositoryImpl implements AdAccountQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<AdAccountDto.Response.Page> mySearch(Pageable pageable, AdAccountDto.Request.MySearch searchRequest) {
        List<AdAccountDto.Response.Page> content = this.query.select(new QAdAccountDto_Response_Page(
                adAccount.id,
                adAccount.name,
                adAccount.companyType,
                adAccount.creditLimit,
                adAccount.preDeferredPaymentYn,
                adAccount.config,
                adAccount.adminStopYn,
                adAccount.outOfBalanceYn))
                .from(adAccount)
                .where(
                        this.idEq(searchRequest.getId()),
                        this.nameContains(searchRequest.getName())
                        )
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Campaign.class, "adaccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
                .where(
                        this.idEq(searchRequest.getId()),
                        this.nameContains(searchRequest.getName()))
                .from(adAccount);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression idEq(Integer id) {
        return id != null ? adAccount.id.eq(id) : null;
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? adAccount.name.contains(name) : null;
    }
}
