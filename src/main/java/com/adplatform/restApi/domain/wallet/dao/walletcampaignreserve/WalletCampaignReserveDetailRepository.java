package com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve;

import com.adplatform.restApi.domain.wallet.domain.WalletCampaignReserveDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCampaignReserveDetailRepository extends JpaRepository<WalletCampaignReserveDetail, Integer>, WalletCampaignReserveDetailQuerydslRepository {
}
