package com.adplatform.restApi.domain.campaign.dao.campaign;

import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CampaignQuerydslRepository {
    Page<CampaignDto.Response.ForSaveAdGroup> searchForSaveAdGroup(CampaignDto.Request.Search request, Pageable pageable);

    CampaignDto.Response.ForUpdate searchForUpdate(Integer campaignId);

    CampaignDto.Response.ForDateSave dateForSave(Integer campaignId);

    CampaignDto.Response.ForDateUpdate dateForUpdate(Integer adGroupId);

}
