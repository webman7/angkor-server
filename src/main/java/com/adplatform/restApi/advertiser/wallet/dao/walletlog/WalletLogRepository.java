package com.adplatform.restApi.advertiser.wallet.dao.walletlog;

import com.adplatform.restApi.advertiser.wallet.domain.WalletLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletLogRepository extends JpaRepository<WalletLog, Integer>, WalletLogQuerydslRepository {
}
