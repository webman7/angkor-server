package com.adplatform.restApi.domain.adgroup.service;

import com.adplatform.restApi.domain.adgroup.dao.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.dao.DeviceRepository;
import com.adplatform.restApi.domain.adgroup.dao.MediaRepository;
import com.adplatform.restApi.domain.adgroup.domain.Device;
import com.adplatform.restApi.domain.adgroup.domain.Media;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupMapper;
import com.adplatform.restApi.domain.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.adgroup.exception.DeviceNotFoundException;
import com.adplatform.restApi.domain.adgroup.exception.MediaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AdGroupService {
    private final AdGroupRepository adGroupRepository;
    private final MediaRepository mediaRepository;
    private final DeviceRepository deviceRepository;
    private final AdGroupMapper adGroupMapper;

    public void save(AdGroupSavedEvent event) {
        List<Media> media = event.getMedia().stream().map(this::findMediaByNameOrElseThrow).collect(Collectors.toList());
        List<Device> devices = event.getDevices().stream().map(this::findDeviceByNameOrElseThrow).collect(Collectors.toList());
        this.adGroupRepository.save(this.adGroupMapper.toEntity(event, media, devices));
    }

    private Media findMediaByNameOrElseThrow(String name) {
        return this.mediaRepository.findByName(name)
                .orElseThrow(MediaNotFoundException::new);
    }

    private Device findDeviceByNameOrElseThrow(String name) {
        return this.deviceRepository.findByName(name)
                .orElseThrow(DeviceNotFoundException::new);
    }
}
