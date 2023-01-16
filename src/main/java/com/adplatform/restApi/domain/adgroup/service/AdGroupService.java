package com.adplatform.restApi.domain.adgroup.service;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.dao.device.DeviceRepository;
import com.adplatform.restApi.domain.adgroup.dao.media.MediaRepository;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.domain.Device;
import com.adplatform.restApi.domain.adgroup.domain.Media;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupMapper;
import com.adplatform.restApi.domain.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.adgroup.exception.AdGroupCopyCashException;
import com.adplatform.restApi.domain.adgroup.exception.AdGroupNotFoundException;
import com.adplatform.restApi.domain.adgroup.exception.DeviceNotFoundException;
import com.adplatform.restApi.domain.adgroup.exception.MediaNotFoundException;
import com.adplatform.restApi.domain.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.exception.CampaignCashException;
import com.adplatform.restApi.domain.campaign.service.CampaignFindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class AdGroupService {
    private final CampaignRepository campaignRepository;
    private final AdGroupRepository adGroupRepository;
    private final MediaRepository mediaRepository;
    private final DeviceRepository deviceRepository;
    private final AdGroupMapper adGroupMapper;

    public void save(AdGroupSavedEvent event) {
        List<Media> media = this.findByMediaName(event.getMedia());
        List<Device> devices = this.findByDeviceName(event.getDevices());
        this.adGroupRepository.save(this.adGroupMapper.toEntity(event, media, devices));

        CampaignDto.Response.ForDateSave campaign = this.campaignRepository.dateForSave(event.getCampaign().getId());
        CampaignFindUtils.findByIdOrElseThrow(campaign.getCampaignId(), this.campaignRepository).saveStartEndDate(campaign);
    }

    private List<Media> findByMediaName(List<String> mediaNames) {
        return mediaNames.stream().map(this::findMediaByNameOrElseThrow).collect(Collectors.toList());
    }

    private List<Device> findByDeviceName(List<String> deviceNames) {
        return deviceNames.stream().map(this::findDeviceByNameOrElseThrow).collect(Collectors.toList());
    }

    private Media findMediaByNameOrElseThrow(String name) {
        return this.mediaRepository.findByName(name)
                .orElseThrow(MediaNotFoundException::new);
    }

    private Device findDeviceByNameOrElseThrow(String name) {
        return this.deviceRepository.findByName(name)
                .orElseThrow(DeviceNotFoundException::new);
    }

    public void update(AdGroupDto.Request.Update request) {
        this.mediaRepository.deleteByAdGroupId(request.getAdGroupId());
        this.deviceRepository.deleteByAdGroupId(request.getAdGroupId());

        List<Media> media = this.findByMediaName(request.getMedia());
        List<Device> devices = this.findByDeviceName(request.getDevices());
        AdGroupFindUtils.findByIdOrElseThrow(request.getAdGroupId(), this.adGroupRepository).update(request, media, devices);

        CampaignDto.Response.ForDateUpdate campaign = this.campaignRepository.dateForUpdate(request.getAdGroupId());
        CampaignFindUtils.findByIdOrElseThrow(campaign.getCampaignId(), this.campaignRepository).updateStartEndDate(campaign);
    }

    public void delete(Integer id) {
        AdGroupFindUtils.findByIdOrElseThrow(id, this.adGroupRepository).delete();
    }

    public void copy(AdGroupDto.Request.@Valid Copy request) {
        Campaign campaign = CampaignFindUtils.findByIdOrElseThrow(request.getCampaignId(), this.campaignRepository);
        // Budget Calculate
        List<CampaignDto.Response.Budget> campaigns = this.campaignRepository.getBudget(campaign.getId());
        Long campaignBudgetSum = 0L;
        for(CampaignDto.Response.Budget item :campaigns) {
            campaignBudgetSum += item.getBudgetAmount();
        }

        List<AdGroupDto.Response.Budget> adGroups = this.adGroupRepository.getBudget(campaign.getId());
        Long adGroupBudgetSum = 0L;
        for(AdGroupDto.Response.Budget item :adGroups) {
            adGroupBudgetSum += item.getBudgetAmount();
        }

        AdGroup adGroup = AdGroupFindUtils.findByIdOrElseThrow(request.getAdGroupId(), this.adGroupRepository);
        Long adGroupBudgetAmount = adGroup.getBudgetAmount();

        if(campaignBudgetSum < (adGroupBudgetSum + adGroupBudgetAmount)) {
            throw new AdGroupCopyCashException();
        }


        AdGroup copiedAdGroup = AdGroupFindUtils.findByIdOrElseThrow(request.getAdGroupId(), this.adGroupRepository).copy(request, campaign);
        this.adGroupRepository.save(copiedAdGroup);
    }

    public void changeConfig(Integer id, AdGroup.Config config) {
        AdGroup adGroup = AdGroupFindUtils.findByIdOrElseThrow(id, this.adGroupRepository);
        if (config == AdGroup.Config.ON) adGroup.changeConfigOn();
        else if (config == AdGroup.Config.OFF) adGroup.changeConfigOff();
    }
}
