package com.adplatform.restApi.domain.advertiser.adgroup.service;

import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.mapper.AdGroupQueryMapper;
import com.adplatform.restApi.domain.advertiser.adgroup.dao.device.DeviceRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.Device;
import com.adplatform.restApi.domain.history.dao.AdminStopHistoryRepository;
import com.adplatform.restApi.domain.history.domain.AdminStopHistory;
import com.adplatform.restApi.domain.history.dto.AdminStopHistoryDto;
import com.adplatform.restApi.domain.history.dto.AdminStopHistoryMapper;
import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupMapper;
import com.adplatform.restApi.domain.advertiser.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.advertiser.adgroup.exception.AdGroupCopyCashException;
import com.adplatform.restApi.domain.advertiser.adgroup.exception.DeviceNotFoundException;
import com.adplatform.restApi.domain.advertiser.adgroup.exception.MediaNotFoundException;
import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.advertiser.campaign.service.CampaignFindUtils;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author junny
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
    private final AdGroupQueryMapper adGroupQueryMapper;
    private final AdminStopHistoryMapper adminStopHistoryMapper;
    private final AdminStopHistoryRepository adminStopHistoryRepository;

    public void save(AdGroupSavedEvent event) {
        List<Media> media = this.findByMediaId(event.getMedia());
        List<Device> devices = this.findByDeviceName(event.getDevices());
        this.adGroupRepository.save(this.adGroupMapper.toEntity(event, media, devices));

        CampaignDto.Response.ForDateSave campaign = this.campaignRepository.dateForSave(event.getCampaign().getId());
        CampaignFindUtils.findByIdOrElseThrow(campaign.getCampaignId(), this.campaignRepository).saveStartEndDate(campaign);
    }

    private List<Media> findByMediaId(List<Integer> mediaId) {
        return mediaId.stream().map(this::findMediaByIdOrElseThrow).collect(Collectors.toList());
    }

    private List<Media> findByMediaName(List<String> mediaNames) {
        return mediaNames.stream().map(this::findMediaByNameOrElseThrow).collect(Collectors.toList());
    }

    private List<Device> findByDeviceName(List<String> deviceNames) {
        return deviceNames.stream().map(this::findDeviceByNameOrElseThrow).collect(Collectors.toList());
    }

    private Media findMediaByIdOrElseThrow(Integer id) {
        return this.mediaRepository.findById(id)
                .orElseThrow(MediaNotFoundException::new);
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
        if (config == AdGroup.Config.ON) {
            // 기간 체크하여 Status 변경
            if(adGroup.getSystemConfig().equals(AdGroup.SystemConfig.ON)) {
                adGroup.changeConfigOn();

                Calendar calendar = new GregorianCalendar();
                SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

                Integer chkDate = Integer.parseInt(SDF.format(calendar.getTime()));
                if(adGroup.getAdGroupSchedule().getStartDate() > chkDate) {
                    adGroup.changeStatusReady();
                } else if (adGroup.getAdGroupSchedule().getStartDate() <= chkDate && adGroup.getAdGroupSchedule().getEndDate() >= chkDate) {
                    adGroup.changeStatusLive();
                } else {
                    adGroup.changeStatusFinished();
                }
            }
        }
        else if (config == AdGroup.Config.OFF) {
            adGroup.changeConfigOff();
            adGroup.changeStatusOff();
        }
    }

    public void changeAdminStop(Integer id, AdGroupDto.Request.AdminStop request, Boolean adminStop) {
        AdGroup adGroup = AdGroupFindUtils.findByIdOrElseThrow(id, this.adGroupRepository);
        if (adminStop) {
            // 히스토리 저장
            AdminStopHistoryDto.Request.Save history = new AdminStopHistoryDto.Request.Save();
            history.setType(request.getType());
            history.setStopId(id);
            history.setReason(request.getReason());
            AdminStopHistory adminStopHistory = this.adminStopHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
            this.adminStopHistoryRepository.save(adminStopHistory);

            // 관리자 정지
            adGroup.changeAdminStopOn();
        }
        else {
            adGroup.changeAdminStopOff();
        }
    }
}
