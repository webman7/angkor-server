package com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve;

import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletCampaignReserve;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.adplatform.restApi.domain.business.domain.QBusinessAccount.businessAccount;
import static com.adplatform.restApi.domain.wallet.domain.QWalletCampaignReserve.walletCampaignReserve;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;

@RequiredArgsConstructor
@Repository
public class WalletCampaignReserveQuerydslRepositoryImpl implements WalletCampaignReserveQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public WalletDto.Response.WalletCampaignReserve getCampaignReserveAmount(Integer businessAccountId, Integer adAccountId, Integer campaignId) {
        return this.query.select(new QWalletDto_Response_WalletCampaignReserve(
                        walletCampaignReserve.reserveAmount
                ))
                .from(businessAccount, walletCampaignReserve)
                .where(businessAccount.id.eq(walletCampaignReserve.id.businessAccountId),
                        walletCampaignReserve.id.businessAccountId.eq(businessAccountId),
                        walletCampaignReserve.id.adAccountId.eq(adAccountId),
                        walletCampaignReserve.id.campaignId.eq(campaignId)
                )
                .fetchOne();
    }

    public void updateCampaignReserveAmount(Integer businessAccountId, Integer adAccountId, Integer campaignId, Float reserveAmount) {
        LocalDateTime now = LocalDateTime.now();

        this.query.update(walletCampaignReserve)
                .set(walletCampaignReserve.reserveAmount, reserveAmount)
                .set(walletCampaignReserve.updatedAt, now)
                .where(walletCampaignReserve.id.businessAccountId.eq(businessAccountId),
                        walletCampaignReserve.id.adAccountId.eq(adAccountId),
                        walletCampaignReserve.id.campaignId.eq(campaignId))
                .execute();
    }
}
