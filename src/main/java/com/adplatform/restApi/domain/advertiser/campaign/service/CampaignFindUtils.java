package com.adplatform.restApi.domain.advertiser.campaign.service;

import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.advertiser.campaign.exception.CampaignNotFoundException;

/**
 * @author junny
 * @since 1.0
 */
public class CampaignFindUtils {
    public static Campaign findByIdOrElseThrow(Integer id, CampaignRepository repository) {
        return repository.findById(id).orElseThrow(CampaignNotFoundException::new);
    }
}
