package com.adplatform.restApi.domain.business.dao.account;

import com.adplatform.restApi.domain.business.domain.BusinessAccountPreDeferredPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessAccountPreDeferredPaymentRepository extends JpaRepository<BusinessAccountPreDeferredPayment, Integer>, BusinessAccountPreDeferredPaymentQuerydslRepository {
}
