package com.adplatform.restApi.statistics.dao.sale;

import com.adplatform.restApi.statistics.domain.sale.SaleRemainAmountDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRemainAmountDailyRepository extends JpaRepository<SaleRemainAmountDaily, Integer> {
}
