package com.adplatform.restApi.domain.statistics.dao.taxbill;

import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBillMonthly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaTaxBillMonthlyRepository extends JpaRepository<MediaTaxBillMonthly, Integer> {
}
