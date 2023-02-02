package com.adplatform.restApi.advertiser.wallet.dao.walletfreecash;

import com.adplatform.restApi.advertiser.wallet.domain.WalletFreeCash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletFreeCashRepository extends JpaRepository<WalletFreeCash, Integer>, WalletFreeCashQuerydslRepository {
}
