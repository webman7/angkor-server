package com.adplatform.restApi.domain.statistics.dao.taxbill;

import com.adplatform.restApi.domain.statistics.domain.taxbill.AdAccountTaxBillMonthly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAccountTaxBillMonthlyRepository extends JpaRepository<AdAccountTaxBillMonthly, Integer> {
}
