package com.adplatform.restApi.advertiser.history.dao.campaign;

import com.adplatform.restApi.advertiser.history.domain.CampaignBudgetChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignBudgetChangeHistoryRepository extends JpaRepository<CampaignBudgetChangeHistory, Integer>, CampaignBudgetChangeHistoryQuerydslRepository  {
}
