package com.adplatform.restApi.advertiser.wallet.dao.walletcashtotal;

import com.adplatform.restApi.advertiser.wallet.domain.WalletCashTotal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCashTotalRepository extends JpaRepository<WalletCashTotal, Integer>, WalletCashTotalQuerydslRepository {
}
