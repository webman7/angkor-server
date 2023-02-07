package com.adplatform.restApi.wallet.dao.walletcashtotal;

import com.adplatform.restApi.wallet.domain.WalletCashTotal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCashTotalRepository extends JpaRepository<WalletCashTotal, Integer>, WalletCashTotalQuerydslRepository {
}
