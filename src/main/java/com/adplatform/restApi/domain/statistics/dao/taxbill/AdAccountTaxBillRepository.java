package com.adplatform.restApi.domain.statistics.dao.taxbill;

import com.adplatform.restApi.domain.statistics.domain.taxbill.AdAccountTaxBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAccountTaxBillRepository extends JpaRepository<AdAccountTaxBill, Integer> {
}
