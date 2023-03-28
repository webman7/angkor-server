package com.adplatform.restApi.domain.wallet.dao.walletmaster;

import com.adplatform.restApi.domain.wallet.domain.WalletReserveLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletReserveLogRepository extends JpaRepository<WalletReserveLog, Integer>, WalletReserveLogQuerydslRepository {
}
