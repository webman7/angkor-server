package com.adplatform.restApi.domain.campaign.service;

import com.adplatform.restApi.domain.campaign.dao.CampaignRepository;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CampaignQueryService {
    private final CampaignRepository campaignRepository;

    public Page<CampaignDto.Response.Page> search(Pageable pageable) {
        return this.campaignRepository.search(pageable);
    }
}
