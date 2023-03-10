package com.adplatform.restApi.domain.adaccount.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author junny
 * @since 1.0
 */
public interface AdAccountUserRepository extends JpaRepository<AdAccountUser, Integer>, AdAccountUserQueryRepository {
}
