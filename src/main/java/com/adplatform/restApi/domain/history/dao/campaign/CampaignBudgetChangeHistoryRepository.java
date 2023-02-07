package com.adplatform.restApi.domain.history.dao.campaign;

import com.adplatform.restApi.domain.history.domain.CampaignBudgetChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignBudgetChangeHistoryRepository extends JpaRepository<CampaignBudgetChangeHistory, Integer>, CampaignBudgetChangeHistoryQuerydslRepository  {
}
