package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CampaignRepository extends JpaRepository<Campaign, Integer>, CampaignQuerydslRepository {
}
