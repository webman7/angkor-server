package com.adplatform.restApi.wallet.dao.walletlog;

import com.adplatform.restApi.wallet.domain.WalletLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletLogRepository extends JpaRepository<WalletLog, Integer>, WalletLogQuerydslRepository {
}
