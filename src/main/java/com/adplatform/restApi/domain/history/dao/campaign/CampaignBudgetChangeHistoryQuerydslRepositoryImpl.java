package com.adplatform.restApi.domain.history.dao.campaign;

import com.adplatform.restApi.domain.history.domain.CampaignBudgetChangeHistory;
import com.adplatform.restApi.domain.history.dto.campaign.CampaignBudgetChangeHistoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.adplatform.restApi.domain.history.domain.QCampaignBudgetChangeHistory.campaignBudgetChangeHistory;

@RequiredArgsConstructor
@Repository
public class CampaignBudgetChangeHistoryQuerydslRepositoryImpl implements CampaignBudgetChangeHistoryQuerydslRepository{
}
