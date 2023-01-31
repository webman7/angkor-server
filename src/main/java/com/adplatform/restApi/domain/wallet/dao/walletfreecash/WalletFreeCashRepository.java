package com.adplatform.restApi.domain.wallet.dao.walletfreecash;

import com.adplatform.restApi.domain.wallet.domain.WalletFreeCash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletFreeCashRepository extends JpaRepository<WalletFreeCash, Integer>, WalletFreeCashQuerydslRepository {
}
