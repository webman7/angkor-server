package com.adplatform.restApi.advertiser.campaign.dao.campaign;

import com.adplatform.restApi.advertiser.campaign.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CampaignRepository extends JpaRepository<Campaign, Integer>, CampaignQuerydslRepository {
}
