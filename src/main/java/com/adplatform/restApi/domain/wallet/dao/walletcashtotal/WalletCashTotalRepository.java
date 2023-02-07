package com.adplatform.restApi.domain.wallet.dao.walletcashtotal;

import com.adplatform.restApi.domain.wallet.domain.WalletCashTotal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCashTotalRepository extends JpaRepository<WalletCashTotal, Integer>, WalletCashTotalQuerydslRepository {
}
