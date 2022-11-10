package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAccountRepository extends JpaRepository<AdAccount, Integer>, AdAccountQuerydslRepository {
}
