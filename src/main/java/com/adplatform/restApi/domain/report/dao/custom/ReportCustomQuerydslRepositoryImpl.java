package com.adplatform.restApi.domain.report.dao.custom;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.report.dto.custom.QReportCustomDto_Response_Default;
import com.adplatform.restApi.domain.report.dto.custom.ReportCustomDto;
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

import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.domain.report.domain.QReportCustom.reportCustom;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ReportCustomQuerydslRepositoryImpl implements ReportCustomQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<ReportCustomDto.Response.Default> search(Pageable pageable, ReportCustomDto.Request.Search request) {
        List<ReportCustomDto.Response.Default> content = this.getSearchReportCustomQuery(pageable, request)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(reportCustom.count())
                .from(reportCustom)
                .where(
                        reportCustom.adAccountId.eq(request.getAdAccountId()),
                        this.containsReportLevel(request.getReportLevel())
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    private JPAQuery<ReportCustomDto.Response.Default> getSearchReportCustomQuery(
            Pageable pageable,
            ReportCustomDto.Request.Search request) {

        JPAQuery<ReportCustomDto.Response.Default> query = this.query.select(new QReportCustomDto_Response_Default(
                        reportCustom.id,
                        reportCustom.adAccountId,
                        reportCustom.name,
                        reportCustom.reportLevel,
                        reportCustom.configs,
                        reportCustom.indicators,
                        reportCustom.startDate,
                        reportCustom.endDate))
                .from(reportCustom)
                .where(
                        reportCustom.adAccountId.eq(request.getAdAccountId()),
                        this.containsReportLevel(request.getReportLevel()));

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "reportCustom", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    private BooleanExpression containsReportLevel(String reportLevel) {
        if(reportLevel.equals("ALL")) return null;

        return StringUtils.hasText(reportLevel) ? reportCustom.reportLevel.eq(reportLevel) : null;
    }

}
