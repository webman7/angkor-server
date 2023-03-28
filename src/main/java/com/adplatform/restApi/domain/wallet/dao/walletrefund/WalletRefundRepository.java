package com.adplatform.restApi.domain.wallet.dao.walletrefund;

import com.adplatform.restApi.domain.wallet.domain.WalletRefund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRefundRepository extends JpaRepository<WalletRefund, Integer>, WalletRefundQuerydslRepository {
}
