package com.adplatform.restApi.advertiser.adaccount.dao.user;

import com.adplatform.restApi.advertiser.adaccount.domain.AdAccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdAccountUserRepository extends JpaRepository<AdAccountUser, Integer>, AdAccountUserQueryRepository {
}
