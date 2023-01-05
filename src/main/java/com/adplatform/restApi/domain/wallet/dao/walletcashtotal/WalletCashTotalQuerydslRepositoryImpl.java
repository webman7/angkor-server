package com.adplatform.restApi.domain.wallet.dao.walletcashtotal;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroupDevice.adGroupDevice;
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
}
