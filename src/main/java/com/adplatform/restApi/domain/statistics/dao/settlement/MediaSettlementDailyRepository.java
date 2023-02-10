package com.adplatform.restApi.domain.statistics.dao.settlement;

import com.adplatform.restApi.domain.statistics.domain.settlement.MediaSettlementDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaSettlementDailyRepository extends JpaRepository<MediaSettlementDaily, Integer> {
}
