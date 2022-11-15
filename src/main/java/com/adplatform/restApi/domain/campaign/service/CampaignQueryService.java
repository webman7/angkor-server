package com.adplatform.restApi.domain.campaign.service;

import com.adplatform.restApi.domain.adgroup.service.AdGroupService;
import com.adplatform.restApi.domain.campaign.dao.CampaignRepository;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto.Response.FirstStartDateAndLastEndDate;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CampaignQueryService {
    private final CampaignRepository campaignRepository;
    private final AdGroupService adGroupService;

    public Page<CampaignDto.Response.Page> search(CampaignDto.Request.Search request, Pageable pageable) {
        Page<CampaignDto.Response.Page> pages = this.campaignRepository.search(request, pageable);
        List<Integer> campaignIds = pages.stream().map(CampaignDto.Response.Page::getId).collect(Collectors.toList());
        List<FirstStartDateAndLastEndDate> dates = this.adGroupService.findFirstStartDateAndLastEndDateByCampaignId(campaignIds);
        this.mapToFirstStartDateAndLastEndDate(pages.getContent(), dates);
        return pages;
    }

    private void mapToFirstStartDateAndLastEndDate(
            List<CampaignDto.Response.Page> campaigns,
            List<FirstStartDateAndLastEndDate> dates) {
        Map<Integer, FirstStartDateAndLastEndDate> map = dates.stream()
                .collect(Collectors.toMap(
                        FirstStartDateAndLastEndDate::getCampaignId,
                        date -> date));
        campaigns.forEach(campaign -> campaign
                .setAdGroupSchedulesFirstStartDate(map.get(campaign.getId()).getFirstStartDate())
                .setAdGroupSchedulesLastEndDate(map.get(campaign.getId()).getLastEndDate()));
    }
}
