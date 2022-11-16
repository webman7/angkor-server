package com.adplatform.restApi.domain.adgroup.service;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.dao.device.DeviceRepository;
import com.adplatform.restApi.domain.adgroup.dao.media.MediaRepository;
import com.adplatform.restApi.domain.adgroup.domain.Device;
import com.adplatform.restApi.domain.adgroup.domain.Media;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupMapper;
import com.adplatform.restApi.domain.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.adgroup.exception.DeviceNotFoundException;
import com.adplatform.restApi.domain.adgroup.exception.MediaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto.Response.FirstStartDateAndLastEndDate;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class AdGroupService {
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

    public List<FirstStartDateAndLastEndDate> findFirstStartDateAndLastEndDateByCampaignId(List<Integer> campaignIds) {
        Map<Integer, Integer> firstStartDates = this.adGroupRepository.findScheduleFirstStartDateByCampaignId(campaignIds);
        Map<Integer, Integer> lastEndDates = this.adGroupRepository.findScheduleLastEndDateByCampaignId(campaignIds);
        return campaignIds.stream().map(campaignId -> new FirstStartDateAndLastEndDate(
                campaignId,
                firstStartDates.get(campaignId),
                lastEndDates.get(campaignId)))
                .collect(Collectors.toList());
    }

    public void update(AdGroupDto.Request.Update request) {
        AdGroupFindUtils.findById(request.getAdGroupId(), this.adGroupRepository).update(request);
    }
}
