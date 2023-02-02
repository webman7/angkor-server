package com.adplatform.restApi.advertiser.statistics.dao.sale;

import com.adplatform.restApi.advertiser.statistics.domain.sale.SaleAmountDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleAmountDailyRepository extends JpaRepository<SaleAmountDaily, Integer> {
}
