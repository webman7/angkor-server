package com.adplatform.restApi.statistics.dao.sale;

import com.adplatform.restApi.statistics.domain.sale.SaleAmountDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleAmountDailyRepository extends JpaRepository<SaleAmountDaily, Integer> {
}
