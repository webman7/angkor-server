package com.adplatform.restApi.domain.wallet.dao.walletmaster;

import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletMaster;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.adplatform.restApi.domain.business.domain.QBusinessAccount.businessAccount;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;

@Slf4j
@RequiredArgsConstructor
@Repository
public class WalletMasterQuerydslRepositoryImpl implements WalletMasterQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public WalletDto.Response.WalletMaster getWalletMaster(Integer businessAccountId) {
        return this.query.select(new QWalletDto_Response_WalletMaster(
                        walletMaster.availableAmount,
                        walletMaster.totalReserveAmount
                ))
                .from(businessAccount, walletMaster)
                .where(businessAccount.id.eq(businessAccountId)
                )
                .fetchOne();
    }

    public void updateWalletMaster(Integer businessAccountId, Float availableAmount, Float totalReserveAmount) {
        LocalDateTime now = LocalDateTime.now();

        this.query.update(walletMaster)
                .set(walletMaster.availableAmount, availableAmount)
                .set(walletMaster.totalReserveAmount, totalReserveAmount)
                .set(walletMaster.updatedAt, now)
                .where(walletMaster.businessAccount.id.eq(businessAccountId))
                .execute();
    }
}
