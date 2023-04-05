package com.adplatform.restApi.domain.statistics.dao.taxbill;

import com.adplatform.restApi.domain.statistics.domain.taxbill.BusinessAccountTaxBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessAccountTaxBillRepository extends JpaRepository<BusinessAccountTaxBill, Integer> {
}
