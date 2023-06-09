package com.adplatform.restApi.domain.advertiser.campaign.api;

import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.advertiser.campaign.service.CampaignCommandService;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCampaignId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/campaigns")
public class CampaignCommandApi {
    private final CampaignCommandService campaignCommandService;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@RequestBody @Valid CampaignDto.Request.Save request) {
        this.campaignCommandService.save(request);
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void update(@RequestBody @Valid CampaignDto.Request.Update request) {
        this.campaignCommandService.update(request);
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adgroups")
    public void adGroupSave(@RequestBody @Valid AdGroupDto.Request.Save request) {
        this.campaignCommandService.adGroupSave(request);
    }

    @AuthorizedAdAccountByCampaignId
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.campaignCommandService.delete(id);
    }

    @AuthorizedAdAccountByCampaignId
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/on")
    public void changeConfigOn(@PathVariable Integer id) {
        this.campaignCommandService.changeConfig(id, Campaign.Config.ON);
    }

    @AuthorizedAdAccountByCampaignId
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/off")
    public void changeConfigOff(@PathVariable Integer id) {
        this.campaignCommandService.changeConfig(id, Campaign.Config.OFF);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/stop/on")
    public void changeAdminStopOn(@PathVariable Integer id, @RequestBody @Valid CampaignDto.Request.AdminStop request) {
        this.campaignCommandService.changeAdminStop(id, request, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/stop/off")
    public void changeAdminStopOff(@PathVariable Integer id, @RequestBody @Valid CampaignDto.Request.AdminStop request) {
        this.campaignCommandService.changeAdminStop(id, request, false);
    }

}
