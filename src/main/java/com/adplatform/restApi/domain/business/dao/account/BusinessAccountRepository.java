package com.adplatform.restApi.domain.business.dao.account;

import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface BusinessAccountRepository extends JpaRepository<BusinessAccount, Integer>, BusinessAccountQuerydslRepository {
}
