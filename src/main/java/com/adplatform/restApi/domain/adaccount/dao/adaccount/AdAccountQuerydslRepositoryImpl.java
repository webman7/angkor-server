package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;

@RequiredArgsConstructor
@Repository
public class AdAccountQuerydslRepositoryImpl implements AdAccountQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<AdAccountDto.Response.Page> mySearch(Pageable pageable, AdAccountDto.Request.MySearch searchRequest) {
//        List<AdAccountDto.Response.Page> content = this.query.select(new QAdAccountDto_Response_Page(
//                        adAccount.id,
//                        adAccount.name,
//                        adAccount.companyType,
//                        adAccount.creditLimit,
//                        adAccount.preDeferredPaymentYn,
//                        adAccount.config,
//                        adAccount.adminStopYn,
//                        adAccount.outOfBalanceYn))
//                .from(adAccount)
//                .where(
//                        this.eqId(searchRequest.getId()),
//                        this.containsName(searchRequest.getName()))
//                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize()).fetch();
//
//        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
//                .where(this.eqId(searchRequest.getId()), this.containsName(searchRequest.getName()))
//                .from(adAccount);
//
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
        return null;
    }

    private BooleanExpression eqId(Integer id) {
        return id != null ? adAccount.id.eq(id) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? adAccount.name.contains(name) : null;
    }
}
