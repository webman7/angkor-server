package com.adplatform.restApi.domain.adaccount.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAccountUserRepository extends JpaRepository<AdAccountUser, Integer>, AdAccountUserQueryRepository {
}
