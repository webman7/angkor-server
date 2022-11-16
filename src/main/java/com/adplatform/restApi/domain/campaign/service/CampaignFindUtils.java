package com.adplatform.restApi.domain.campaign.service;

import com.adplatform.restApi.domain.campaign.dao.CampaignRepository;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.exception.CampaignNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CampaignFindUtils {
    public static Campaign findById(Integer id, CampaignRepository repository) {
        return repository.findById(id).orElseThrow(CampaignNotFoundException::new);
    }
}
