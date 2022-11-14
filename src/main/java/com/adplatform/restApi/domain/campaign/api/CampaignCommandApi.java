package com.adplatform.restApi.domain.campaign.api;

import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.service.CampaignSaveService;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campaigns")
public class CampaignCommandApi {
    private final CampaignSaveService campaignSaveService;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@RequestBody @Valid CampaignDto.Request.Save request) {
        this.campaignSaveService.save(request);
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adgroups")
    public void adGroupSave(@RequestBody @Valid AdGroupDto.Request.Save request) {
        this.campaignSaveService.adGroupSave(request);
    }
}
