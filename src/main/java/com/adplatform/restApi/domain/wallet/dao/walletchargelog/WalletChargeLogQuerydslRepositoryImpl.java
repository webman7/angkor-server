package com.adplatform.restApi.domain.wallet.dao.walletchargelog;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_ChargeSearch;
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

import static com.adplatform.restApi.domain.business.domain.QBusinessAccount.businessAccount;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.adplatform.restApi.domain.wallet.domain.QWalletChargeFile.walletChargeFile;
import static com.adplatform.restApi.domain.wallet.domain.QWalletChargeLog.walletChargeLog;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
@Repository
public class WalletChargeLogQuerydslRepositoryImpl implements WalletChargeLogQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<WalletDto.Response.ChargeSearch> searchForCharge(Pageable pageable, WalletDto.Request.ChargeSearch request) {
        List<WalletDto.Response.ChargeSearch> content = this.getSearchForChargeQuery(pageable, request)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(walletChargeLog.count())
                .from(walletChargeLog, businessAccount)
                .where(
                        walletChargeLog.businessAccountId.eq(businessAccount.id),
                        this.eqId(request.getBusinessAccountId()),
//                        walletChargeLog.depositAt.loe(request.getStartDate()),
//                        walletChargeLog.depositAt.goe(request.getEndDate())
                        walletChargeLog.depositAt.between(
                                request.getStartDate(),
                                request.getEndDate()
                        )
                );
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private JPAQuery<WalletDto.Response.ChargeSearch> getSearchForChargeQuery(
            Pageable pageable,
            WalletDto.Request.ChargeSearch request) {

        JPAQuery<WalletDto.Response.ChargeSearch> query = this.query.select(
                        new QWalletDto_Response_ChargeSearch(
                                walletChargeLog.id,
                                walletChargeLog.businessAccountId,
                                businessAccount.name,
                                walletChargeLog.depositAmount,
                                walletChargeLog.depositor,
                                walletChargeLog.depositAt,
                                walletChargeLog.adminMemo,
                                as(select(walletChargeFile.information.url)
                                        .from(walletChargeFile)
                                        .where(walletChargeFile.walletChargeLog.id.eq(walletChargeLog.id),
                                                walletChargeFile.id.eq(select(walletChargeFile.id.max())
                                                        .from(walletChargeFile)
                                                        .where(walletChargeFile.walletChargeLog.id.eq(walletChargeLog.id)
                                                        ))
                                        ), "fileUrl"),
                                as(select(walletChargeFile.information.originalFileName)
                                        .from(walletChargeFile)
                                        .where(walletChargeFile.walletChargeLog.id.eq(walletChargeLog.id),
                                                walletChargeFile.id.eq(select(walletChargeFile.id.max())
                                                        .from(walletChargeFile)
                                                        .where(walletChargeFile.walletChargeLog.id.eq(walletChargeLog.id)
                                                        ))
                                        ), "fileName"),
                                as(select(walletChargeFile.information.fileType)
                                        .from(walletChargeFile)
                                        .where(walletChargeFile.walletChargeLog.id.eq(walletChargeLog.id),
                                                walletChargeFile.id.eq(select(walletChargeFile.id.max())
                                                        .from(walletChargeFile)
                                                        .where(walletChargeFile.walletChargeLog.id.eq(walletChargeLog.id)
                                                        ))
                                        ), "fileType"),
                                walletChargeLog.createdUserNo,
                                as(select(user.loginId)
                                                .from(user)
                                                .where(user.id.eq(walletChargeLog.createdUserNo)),
                                        "createdUserId"),
                                walletChargeLog.createdAt
                        )
                )
                .from(walletChargeLog, businessAccount, walletMaster)
                .where(
                        walletChargeLog.businessAccountId.eq(businessAccount.id),
                        businessAccount.id.eq(walletMaster.businessAccount.id),
                        this.eqId(request.getBusinessAccountId()),
                        walletChargeLog.depositAt.between(
                                request.getStartDate(),
                                request.getEndDate()
                        )
                )
                .orderBy(walletChargeLog.id.desc());

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "walletChargeLog", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

//    @Override
//    public WalletChargeFile findDetailFilesById(Integer id) {
//        return this.query.select(walletChargeLogFile)
//                .from(walletChargeLog, walletChargeLogFile)
//                .where(walletChargeLog.id.eq(id),
//                        walletChargeLog.id.eq(walletChargeLogFile.walletChargeLog.id))
//                .fetchOne();
//    }

    private BooleanExpression eqId(Integer id) {
        return id != null ? businessAccount.id.eq(id) : null;
    }

}
