package com.adplatform.restApi.domain.wallet.dao.walletcashtotal;

import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletCashTotal;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.Expression;
import org.springframework.expression.common.ExpressionUtils;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.wallet.domain.QCash.cash;
import static com.adplatform.restApi.domain.wallet.domain.QWalletCashTotal.walletCashTotal;

@RequiredArgsConstructor
@Repository
public class WalletCashTotalQuerydslRepositoryImpl implements WalletCashTotalQuerydslRepository {

    private final JPAQueryFactory query;

    public void updateWalletCashAdd(Integer adAccountId, Integer cashId, Long amount, Long availableAmount) {
        this.query.update(walletCashTotal)
                .set(walletCashTotal.amount, amount).set(walletCashTotal.availableAmount, availableAmount)
                .where(walletCashTotal.id.walletMasterId.eq(adAccountId),
                        walletCashTotal.id.cashId.eq(cashId))
                .execute();
    }

    @Override
    public List<WalletDto.Response.WalletCashTotal> getWalletCashTotal(Integer adAccountId) {
        return this.query.select(new QWalletDto_Response_WalletCashTotal(
                        walletCashTotal.cash.id,
                        walletCashTotal.amount,
                        walletCashTotal.availableAmount,
                        walletCashTotal.reserveAmount
                ))
                .from(adAccount, walletCashTotal, cash)
                .where(adAccount.id.eq(adAccountId),
                        adAccount.id.eq(walletCashTotal.id.walletMasterId),
                        walletCashTotal.cash.id.eq(cash.id)
                )
                .orderBy(cash.priority.asc())
                .fetch();
    }

    public void saveWalletCashReserve(Integer adAccountId, Integer cashId, Long availableAmount, Long reserveAmount) {
        LocalDateTime now = LocalDateTime.now();

//        // 년, 월(문자열, 숫자), 일(월 기준, 년 기준), 요일(문자열, 숫자), 시, 분, 초 구하기
//        int year = now.getYear();  // 연도
//        String month = now.getMonth().toString();  // 월(문자열)
//        int monthValue = now.getMonthValue();  // 월(숫자)
//        int dayOfMonth = now.getDayOfMonth();  // 일(월 기준)
//        int dayOfYear = now.getDayOfYear();  // 일(년 기준)
//        String dayOfWeek = now.getDayOfWeek().toString();  // 요일(문자열)
//        int dayOfWeekValue = now.getDayOfWeek().getValue();  // 요일(숫자)
//        int hour = now.getHour();
//        int minute = now.getMinute();
//        int second = now.getSecond();

        this.query.update(walletCashTotal)
                .set(walletCashTotal.availableAmount, availableAmount)
                .set(walletCashTotal.reserveAmount, reserveAmount)
                .set(walletCashTotal.updatedAt, now)
                .where(walletCashTotal.id.walletMasterId.eq(adAccountId),
                        walletCashTotal.id.cashId.eq(cashId))
                .execute();
    }
}
