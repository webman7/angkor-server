package com.adplatform.restApi.advertiser.wallet.dao.cash;

import com.adplatform.restApi.advertiser.wallet.domain.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CashRepository extends JpaRepository<Cash, Integer>, CashQuerydslRepository {
}
