package com.adplatform.restApi.domain.statistics.dao.settlement;

import com.adplatform.restApi.domain.statistics.domain.settlement.AdAccountSettlementDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAccountSettlementDailyRepository extends JpaRepository<AdAccountSettlementDaily, Integer> {
}
