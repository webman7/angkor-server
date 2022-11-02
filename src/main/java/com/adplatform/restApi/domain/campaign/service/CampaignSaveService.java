package com.adplatform.restApi.domain.campaign.service;

import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupEventMapper;
import com.adplatform.restApi.domain.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.campaign.dao.AdTypeAndGoalRepository;
import com.adplatform.restApi.domain.campaign.dao.CampaignRepository;
import com.adplatform.restApi.domain.campaign.domain.AdTypeAndGoal;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.dto.CampaignMapper;
import com.adplatform.restApi.domain.campaign.exception.AdTypeAndGoalNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CampaignSaveService {
    private final ApplicationEventPublisher eventPublisher;
    private final CampaignRepository campaignRepository;
    private final AdTypeAndGoalRepository adTypeAndGoalRepository;
    private final CampaignMapper campaignMapper;
    private final AdGroupEventMapper adGroupEventMapper;

    public void save(CampaignDto.Request.Save request) {
        AdTypeAndGoal adTypeAndGoal = this.findAdTypeAndGoal(
                request.getAdTypeAndGoal().getAdTypeName(),
                request.getAdTypeAndGoal().getAdGoalName());
        Campaign campaign = this.campaignRepository.save(this.campaignMapper.toEntity(request, adTypeAndGoal));
        this.mapToAdGroupSavedEvents(request, campaign).forEach(this.eventPublisher::publishEvent);
    }

    private List<AdGroupSavedEvent> mapToAdGroupSavedEvents(CampaignDto.Request.Save request, Campaign campaign) {
        return request.getAdGroups().stream()
                .map(a -> this.adGroupEventMapper.toEvent(a, campaign))
                .collect(Collectors.toList());
    }

    private AdTypeAndGoal findAdTypeAndGoal(String adTypeName, String adGoalName) {
        return this.adTypeAndGoalRepository.findByAdType_NameAndAdGoal_Name(adTypeName, adGoalName)
                .orElseThrow(AdTypeAndGoalNotFoundException::new);
    }
}
