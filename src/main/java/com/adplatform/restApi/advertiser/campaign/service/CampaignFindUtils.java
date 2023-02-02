package com.adplatform.restApi.advertiser.campaign.service;

import com.adplatform.restApi.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.advertiser.campaign.exception.CampaignNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CampaignFindUtils {
    public static Campaign findByIdOrElseThrow(Integer id, CampaignRepository repository) {
        return repository.findById(id).orElseThrow(CampaignNotFoundException::new);
    }
}
