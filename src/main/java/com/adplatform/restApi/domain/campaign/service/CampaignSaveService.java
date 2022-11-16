package com.adplatform.restApi.domain.campaign.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.service.AdAccountFindUtils;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
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

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CampaignSaveService {
    private final ApplicationEventPublisher eventPublisher;
    private final CampaignRepository campaignRepository;
    private final AdTypeAndGoalRepository adTypeAndGoalRepository;
    private final AdAccountRepository adAccountRepository;
    private final CampaignMapper campaignMapper;
    private final AdGroupEventMapper adGroupEventMapper;

    public void save(CampaignDto.Request.Save request) {
        AdTypeAndGoal adTypeAndGoal = this.findAdTypeAndGoal(
                request.getAdTypeAndGoal().getAdTypeName(),
                request.getAdTypeAndGoal().getAdGoalName());
        AdAccount adAccount = AdAccountFindUtils.findById(request.getAdAccountId(), this.adAccountRepository);
        Campaign campaign = this.campaignRepository.save(this.campaignMapper.toEntity(request, adTypeAndGoal, adAccount));
        this.mapToAdGroupSavedEvent(request.getAdGroups(), campaign).forEach(this.eventPublisher::publishEvent);
    }

    private AdTypeAndGoal findAdTypeAndGoal(String adTypeName, String adGoalName) {
        return this.adTypeAndGoalRepository.findByAdType_NameAndAdGoal_Name(adTypeName, adGoalName)
                .orElseThrow(AdTypeAndGoalNotFoundException::new);
    }

    public void adGroupSave(AdGroupDto.Request.Save request) {
        Campaign campaign = CampaignFindUtils.findById(request.getCampaignId(), this.campaignRepository);
        this.mapToAdGroupSavedEvent(request.getAdGroups(), campaign).forEach(this.eventPublisher::publishEvent);
    }

    private List<AdGroupSavedEvent> mapToAdGroupSavedEvent(List<AdGroupDto.Request.FirstSave> request, Campaign campaign) {
        return request.stream().map(a -> this.adGroupEventMapper.toEvent(a, campaign)).collect(Collectors.toList());
    }

    public void update(CampaignDto.Request.Update request) {
        CampaignFindUtils.findById(request.getCampaignId(), this.campaignRepository)
                .update(request);
    }
}
