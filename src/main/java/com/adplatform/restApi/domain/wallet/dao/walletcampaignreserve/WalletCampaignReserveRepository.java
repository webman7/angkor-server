package com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve;

import com.adplatform.restApi.domain.wallet.domain.WalletCampaignReserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCampaignReserveRepository extends JpaRepository<WalletCampaignReserve, Integer>, WalletCampaignReserveQuerydslRepository {
}
