package com.adplatform.restApi.domain.wallet.dao.walletrefund;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.wallet.domain.WalletRefund;
import com.adplatform.restApi.domain.wallet.domain.WalletRefundFile;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_CreditSearch;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_RefundSearch;
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
import static com.adplatform.restApi.domain.media.domain.QMedia.media;
import static com.adplatform.restApi.domain.media.domain.QMediaFile.mediaFile;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;
import static com.adplatform.restApi.domain.wallet.domain.QWalletRefund.walletRefund;
import static com.adplatform.restApi.domain.wallet.domain.QWalletRefundFile.walletRefundFile;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
@Repository
public class WalletRefundQuerydslRepositoryImpl implements WalletRefundQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<WalletDto.Response.RefundSearch> searchForRefund(Pageable pageable, WalletDto.Request.RefundSearch request) {
        List<WalletDto.Response.RefundSearch> content = this.getSearchForRefundQuery(pageable, request)
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

        JPAQuery<Long> countQuery = this.query.select(walletRefund.count())
                .from(walletRefund, businessAccount)
                .where(
                        walletRefund.businessAccountId.eq(businessAccount.id),
                        this.eqId(request.getBusinessAccountId()),
                        this.eqSendYn(request.getSendYn()),
                        walletRefund.createdAt.between(
                                LocalDateTime.of(searchStartDate, LocalTime.MIN),
                                LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0)
                        )
                );
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private JPAQuery<WalletDto.Response.RefundSearch> getSearchForRefundQuery(
            Pageable pageable,
            WalletDto.Request.RefundSearch request) {

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

        JPAQuery<WalletDto.Response.RefundSearch> query = this.query.select(
                        new QWalletDto_Response_RefundSearch(
                                walletRefund.id,
                                walletRefund.businessAccountId,
                                businessAccount.name,
                                walletRefund.bankId,
                                walletRefund.accountNumber,
                                walletRefund.accountOwner,
                                walletMaster.availableAmount,
                                walletRefund.requestAmount,
                                walletRefund.amount,
                                walletRefund.adminMemo,
                                as(select(walletRefundFile.information.url)
                                        .from(walletRefundFile)
                                        .where(walletRefundFile.walletRefund.id.eq(walletRefund.id),
                                                walletRefundFile.id.eq(select(walletRefundFile.id.max())
                                                        .from(walletRefundFile)
                                                        .where(walletRefundFile.walletRefund.id.eq(walletRefund.id)
                                                        ))
                                        ), "fileUrl"),
                                walletRefund.sendYN.stringValue(),
                                walletRefund.createdUserNo,
                                as(select(user.loginId)
                                                .from(user)
                                                .where(user.id.eq(walletRefund.createdUserNo)),
                                        "createdUserId"),
                                walletRefund.createdAt,
                                walletRefund.updatedUserNo,
                                as(select(user.loginId)
                                                .from(user)
                                                .where(user.id.eq(walletRefund.updatedUserNo)),
                                        "updatedUserId"),
                                walletRefund.updatedAt
                        )
                )
                .from(walletRefund, businessAccount, walletMaster)
                .where(
                        walletRefund.businessAccountId.eq(businessAccount.id),
                        businessAccount.id.eq(walletMaster.businessAccount.id),
                        this.eqId(request.getBusinessAccountId()),
                        this.eqSendYn(request.getSendYn()),
                        walletRefund.createdAt.between(
                                LocalDateTime.of(searchStartDate, LocalTime.MIN),
                                LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0)
                        )
                )
                .orderBy(walletRefund.id.desc());

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "walletRefund", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

//    @Override
//    public WalletRefundFile findDetailFilesById(Integer id) {
//        return this.query.select(walletRefundFile)
//                .from(walletRefund, walletRefundFile)
//                .where(walletRefund.id.eq(id),
//                        walletRefund.id.eq(walletRefundFile.walletRefund.id))
//                .fetchOne();
//    }

    private BooleanExpression eqId(Integer id) {
        return id != null ? businessAccount.id.eq(id) : null;
    }

    private BooleanExpression eqSendYn(String sendYn) {
        if(sendYn.equals("Y")) {
            return walletRefund.sendYN.eq(WalletRefund.SendYN.Y);
        } else if(sendYn.equals("N")) {
            return walletRefund.sendYN.eq(WalletRefund.SendYN.N);
        } else if(sendYn.equals("R")) {
            return walletRefund.sendYN.eq(WalletRefund.SendYN.R);
        } else {
            return null;
        }
    }
}
