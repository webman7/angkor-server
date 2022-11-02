package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Integer>, CampaignQuerydslRepository {
}
