package com.adplatform.restApi.domain.advertiser.campaign.dao.campaign;

import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
public interface CampaignQuerydslRepository {
    Page<CampaignDto.Response.ForSaveAdGroup> searchForSaveAdGroup(CampaignDto.Request.Search request, Pageable pageable);

    CampaignDto.Response.ForUpdate searchForUpdate(Integer campaignId);

    CampaignDto.Response.ForDateSave dateForSave(Integer campaignId);

    CampaignDto.Response.ForDateUpdate dateForUpdate(Integer adGroupId);

    List<CampaignDto.Response.Budget> getBudget(Integer campaignId);

    CampaignDto.Response.CampaignByAdAccountId getCampaignByAdAccountId(Integer campaignId);

}
