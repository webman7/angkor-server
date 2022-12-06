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
import com.adplatform.restApi.domain.adgroup.exception.DeviceNotFoundException;
import com.adplatform.restApi.domain.adgroup.exception.MediaNotFoundException;
import com.adplatform.restApi.domain.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
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
        AdGroupFindUtils.findByIdOrElseThrow(request.getAdGroupId(), this.adGroupRepository).update(request);
    }

    public void delete(Integer id) {
        AdGroupFindUtils.findByIdOrElseThrow(id, this.adGroupRepository).delete();
    }

    public void copy(AdGroupDto.Request.@Valid Copy request) {
        Campaign campaign = CampaignFindUtils.findByIdOrElseThrow(request.getCampaignId(), this.campaignRepository);
        AdGroup copiedAdGroup = AdGroupFindUtils.findByIdOrElseThrow(request.getAdGroupId(), this.adGroupRepository).copy(request, campaign);
        this.adGroupRepository.save(copiedAdGroup);
    }

    public void changeConfig(Integer id, AdGroup.Config config) {
        AdGroup adGroup = AdGroupFindUtils.findByIdOrElseThrow(id, this.adGroupRepository);
        if (config == AdGroup.Config.ON) adGroup.changeConfigOn();
        else if (config == AdGroup.Config.OFF) adGroup.changeConfigOff();
    }
}
