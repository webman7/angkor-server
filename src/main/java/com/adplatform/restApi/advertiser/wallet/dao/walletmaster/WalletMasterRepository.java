package com.adplatform.restApi.advertiser.wallet.dao.walletmaster;

import com.adplatform.restApi.advertiser.wallet.domain.WalletMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletMasterRepository extends JpaRepository<WalletMaster, Integer>, WalletMasterQuerydslRepository {
}
