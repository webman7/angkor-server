package com.adplatform.restApi.history.dao.campaign;

import com.adplatform.restApi.history.domain.CampaignBudgetChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignBudgetChangeHistoryRepository extends JpaRepository<CampaignBudgetChangeHistory, Integer>, CampaignBudgetChangeHistoryQuerydslRepository  {
}
