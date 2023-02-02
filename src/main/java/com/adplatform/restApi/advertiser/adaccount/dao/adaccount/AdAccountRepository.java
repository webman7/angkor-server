package com.adplatform.restApi.advertiser.adaccount.dao.adaccount;

import com.adplatform.restApi.advertiser.adaccount.domain.AdAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdAccountRepository extends JpaRepository<AdAccount, Integer>, AdAccountQuerydslRepository {
}
