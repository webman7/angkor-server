package com.adplatform.restApi.domain.wallet.dao.walletmaster;

import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletMasterRepository extends JpaRepository<WalletMaster, Integer>, WalletMasterQuerydslRepository {
}
