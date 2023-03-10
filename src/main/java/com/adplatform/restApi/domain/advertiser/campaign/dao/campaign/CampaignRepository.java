package com.adplatform.restApi.domain.advertiser.campaign.dao.campaign;

import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author junny
 * @since 1.0
 */
public interface CampaignRepository extends JpaRepository<Campaign, Integer>, CampaignQuerydslRepository {
}
