package com.adplatform.restApi.domain.campaign.service;

import com.adplatform.restApi.domain.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.exception.CampaignNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CampaignFindUtils {
    public static Campaign findByIdOrElseThrow(Integer id, CampaignRepository repository) {
        return repository.findById(id).orElseThrow(CampaignNotFoundException::new);
    }
}
