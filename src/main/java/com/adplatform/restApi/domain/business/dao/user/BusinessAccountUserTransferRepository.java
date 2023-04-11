package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUserTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessAccountUserTransferRepository extends JpaRepository<BusinessAccountUserTransfer, Integer> {
}
