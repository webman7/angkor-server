package com.adplatform.restApi.domain.wallet.dao.walletlog;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_CreditSearch;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccount.businessAccount;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.adplatform.restApi.domain.wallet.domain.QWalletLog.walletLog;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
@Repository
public class WalletLogQuerydslRepositoryImpl implements WalletLogQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<WalletDto.Response.CreditSearch> searchForCreditLog(Pageable pageable, WalletDto.Request.CreditSearch request) {
        List<WalletDto.Response.CreditSearch> content = this.getSearchForCreditLogQuery(pageable, request)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        String startDate = "";
        String endDate = "";
        if(request.getStartDate() == null || request.getStartDate().equals("") || request.getEndDate() == null || request.getEndDate().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            endDate = sdf.format(c.getTime());

            c.add(Calendar.DATE, -7);
            startDate = sdf.format(c.getTime());
        } else {
            String startYear = request.getStartDate().substring(0, 4);
            String startMonth = request.getStartDate().substring(4, 6);
            String startDay = request.getStartDate().substring(6, 8);
            startDate = startYear + "-" + startMonth + "-" + startDay;

            String endYear = request.getEndDate().substring(0, 4);
            String endMonth = request.getEndDate().substring(4, 6);
            String endDay = request.getEndDate().substring(6, 8);
            endDate = endYear + "-" + endMonth + "-" + endDay;
        }

        LocalDate searchStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate searchEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        JPAQuery<Long> countQuery = this.query.select(walletLog.count())
                .from(walletLog, businessAccount)
                .where(
                        walletLog.businessAccountId.eq(businessAccount.id),
                        this.eqId(request.getBusinessAccountId()),
                        this.eqSummary(request.getSummary()),
                        walletLog.createdAt.between(
                                LocalDateTime.of(searchStartDate, LocalTime.MIN),
                                LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0)
                        )
                );
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private JPAQuery<WalletDto.Response.CreditSearch> getSearchForCreditLogQuery(
            Pageable pageable,
            WalletDto.Request.CreditSearch request) {

        String startDate = "";
        String endDate = "";
        if(request.getStartDate() == null || request.getStartDate().equals("") || request.getEndDate() == null || request.getEndDate().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            endDate = sdf.format(c.getTime());

            c.add(Calendar.DATE, -7);
            startDate = sdf.format(c.getTime());
        } else {
            String startYear = request.getStartDate().substring(0, 4);
            String startMonth = request.getStartDate().substring(4, 6);
            String startDay = request.getStartDate().substring(6, 8);
            startDate = startYear + "-" + startMonth + "-" + startDay;

            String endYear = request.getEndDate().substring(0, 4);
            String endMonth = request.getEndDate().substring(4, 6);
            String endDay = request.getEndDate().substring(6, 8);
            endDate = endYear + "-" + endMonth + "-" + endDay;
        }

        LocalDate searchStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate searchEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        JPAQuery<WalletDto.Response.CreditSearch> query = this.query.select(
                        new QWalletDto_Response_CreditSearch(
                                walletLog.id,
                                walletLog.businessAccountId,
                                walletLog.summary,
                                walletLog.changeAmount,
                                walletLog.availableAmount,
                                walletLog.changeAvailableAmount,
                                walletLog.memo,
                                walletLog.createdUserNo,
                                as(select(user.loginId)
                                                .from(user)
                                                .where(user.id.eq(walletLog.createdUserNo)),
                                        "createdUserId"),
                                walletLog.createdAt
                        )
                )
                .from(walletLog, businessAccount)
                .where(
                        walletLog.businessAccountId.eq(businessAccount.id),
                        this.eqId(request.getBusinessAccountId()),
                        this.eqSummary(request.getSummary()),
                        walletLog.createdAt.between(
                                LocalDateTime.of(searchStartDate, LocalTime.MIN),
                                LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0)
                        )
                ).orderBy(walletLog.id.desc());

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "walletLog", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    private BooleanExpression eqId(Integer id) {
        return id != null ? businessAccount.id.eq(id) : null;
    }

    private BooleanExpression eqSummary(String summary) {
        return (summary != null && !summary.equals("")) ? walletLog.summary.eq(summary) : null;
    }
}
