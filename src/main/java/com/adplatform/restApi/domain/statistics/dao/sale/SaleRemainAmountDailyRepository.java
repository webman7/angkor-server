package com.adplatform.restApi.domain.statistics.dao.sale;

import com.adplatform.restApi.domain.statistics.domain.sale.SaleRemainAmountDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRemainAmountDailyRepository extends JpaRepository<SaleRemainAmountDaily, Integer> {
}
