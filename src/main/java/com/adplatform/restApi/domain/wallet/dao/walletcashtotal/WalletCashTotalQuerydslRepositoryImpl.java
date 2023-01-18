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

        this.query.update(walletCashTotal)
                .set(walletCashTotal.availableAmount, availableAmount)
                .set(walletCashTotal.reserveAmount, reserveAmount)
                .set(walletCashTotal.updatedAt, now)
                .where(walletCashTotal.id.walletMasterId.eq(adAccountId),
                        walletCashTotal.id.cashId.eq(cashId))
                .execute();
    }

    public void saveWalletCashSettle(Integer adAccountId, Integer cashId, Long amount, Long reserveAmount) {
        LocalDateTime now = LocalDateTime.now();

        this.query.update(walletCashTotal)
                .set(walletCashTotal.amount, amount)
                .set(walletCashTotal.reserveAmount, reserveAmount)
                .set(walletCashTotal.updatedAt, now)
                .where(walletCashTotal.id.walletMasterId.eq(adAccountId),
                        walletCashTotal.id.cashId.eq(cashId))
                .execute();
    }
}
