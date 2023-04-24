package com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class WalletCampaignReserveDetailQuerydslRepositoryImpl implements WalletCampaignReserveDetailQuerydslRepository {
    private final JPAQueryFactory query;
}
