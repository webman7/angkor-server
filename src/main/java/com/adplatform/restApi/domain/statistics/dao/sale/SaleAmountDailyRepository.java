package com.adplatform.restApi.domain.statistics.dao.sale;

import com.adplatform.restApi.domain.statistics.domain.sale.SaleAmountDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleAmountDailyRepository extends JpaRepository<SaleAmountDaily, Integer> {
}
