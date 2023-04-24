package com.adplatform.restApi.domain.wallet.dao.walletmaster;

import com.adplatform.restApi.domain.wallet.domain.WalletMasterDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletMasterDetailRepository extends JpaRepository<WalletMasterDetail, Integer> {
}
