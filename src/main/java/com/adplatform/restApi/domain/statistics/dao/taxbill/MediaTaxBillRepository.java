package com.adplatform.restApi.domain.statistics.dao.taxbill;

import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaTaxBillRepository extends JpaRepository<MediaTaxBill, Integer>, MediaTaxBillQuerydslRepository {
}
