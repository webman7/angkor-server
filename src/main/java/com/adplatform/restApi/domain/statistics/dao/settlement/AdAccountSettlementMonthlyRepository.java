package com.adplatform.restApi.domain.statistics.dao.settlement;

import com.adplatform.restApi.domain.statistics.domain.settlement.AdAccountSettlementMonthly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAccountSettlementMonthlyRepository extends JpaRepository<AdAccountSettlementMonthly, Integer> {
}
