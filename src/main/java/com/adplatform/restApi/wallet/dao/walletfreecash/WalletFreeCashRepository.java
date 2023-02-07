package com.adplatform.restApi.wallet.dao.walletfreecash;

import com.adplatform.restApi.wallet.domain.WalletFreeCash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletFreeCashRepository extends JpaRepository<WalletFreeCash, Integer>, WalletFreeCashQuerydslRepository {
}
