package com.adplatform.restApi.domain.wallet.dao.walletlog;

import com.adplatform.restApi.domain.wallet.domain.WalletLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletLogRepository extends JpaRepository<WalletLog, Integer>, WalletLogQuerydslRepository {
}
