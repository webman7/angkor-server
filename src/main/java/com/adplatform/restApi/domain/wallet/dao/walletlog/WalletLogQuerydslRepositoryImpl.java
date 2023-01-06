package com.adplatform.restApi.domain.wallet.dao.walletlog;

import com.adplatform.restApi.domain.wallet.domain.Cash;
import com.adplatform.restApi.domain.wallet.domain.QCash;
import com.adplatform.restApi.domain.wallet.domain.QWalletCashTotal;
import com.adplatform.restApi.domain.wallet.domain.WalletCashTotal;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletCashTotal;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.wallet.domain.QWalletCashTotal.walletCashTotal;
import static com.adplatform.restApi.domain.wallet.domain.QWalletLog.walletLog;

@RequiredArgsConstructor
@Repository
public class WalletLogQuerydslRepositoryImpl implements WalletLogQuerydslRepository {
    private final JPAQueryFactory query;

    public Integer getNewTradeNo(Integer adAccountId) {
        return this.query.select(walletLog.tradeNo.max().coalesce(0))
                .from(adAccount, walletLog)
                .where(adAccount.id.eq(adAccountId),
                        adAccount.id.eq(walletLog.id)
                ).fetchOne();
    }

    public WalletDto.Response.WalletCashTotal getCashTotalByCashId(Integer adAccountId, Integer cashId) {
        return this.query.select(new QWalletDto_Response_WalletCashTotal(
                        walletCashTotal.cash.id,
                        walletCashTotal.amount,
                        walletCashTotal.availableAmount,
                        walletCashTotal.reserveAmount
                    ))
                .from(adAccount, walletCashTotal)
                .where(adAccount.id.eq(adAccountId),
                        walletCashTotal.id.cashId.eq(cashId),
                        adAccount.id.eq(walletCashTotal.id.walletMasterId)
                ).fetchOne();
    }
}
