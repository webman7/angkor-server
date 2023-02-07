package com.adplatform.restApi.wallet.dao.walletmaster;

import com.adplatform.restApi.wallet.domain.WalletMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletMasterRepository extends JpaRepository<WalletMaster, Integer>, WalletMasterQuerydslRepository {
}
