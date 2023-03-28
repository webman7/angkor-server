package com.adplatform.restApi.domain.wallet.dao.walletchargelog;

import com.adplatform.restApi.domain.wallet.domain.WalletChargeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletChargeLogRepository extends JpaRepository<WalletChargeLog, Integer>, WalletChargeLogQuerydslRepository  {
}
