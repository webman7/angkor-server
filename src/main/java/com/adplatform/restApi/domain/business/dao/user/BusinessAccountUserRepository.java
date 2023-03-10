package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface BusinessAccountUserRepository extends JpaRepository<BusinessAccountUser, Integer>, BusinessAccountUserQueryRepository {
}
