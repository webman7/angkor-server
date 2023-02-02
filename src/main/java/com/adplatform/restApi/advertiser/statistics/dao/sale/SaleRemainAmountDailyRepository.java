package com.adplatform.restApi.advertiser.statistics.dao.sale;

import com.adplatform.restApi.advertiser.statistics.domain.sale.SaleRemainAmountDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRemainAmountDailyRepository extends JpaRepository<SaleRemainAmountDaily, Integer> {
}
