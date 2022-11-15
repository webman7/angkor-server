package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CampaignQuerydslRepository {
    Page<CampaignDto.Response.Page> search(CampaignDto.Request.Search request, Pageable pageable);

    Page<CampaignDto.Response.ForSaveAdGroup> searchForSaveAdGroup(CampaignDto.Request.Search request, Pageable pageable);

    CampaignDto.Response.ForUpdate searchForUpdate(Integer campaignId);
}
