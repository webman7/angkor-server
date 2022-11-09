package com.adplatform.restApi.domain.campaign.api;

import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.service.CampaignQueryService;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campaigns/search")
public class CampaignQueryApi {
    private final CampaignQueryService campaignQueryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PageDto<CampaignDto.Response.Page> search(@PageableDefault Pageable pageable) {
        return PageDto.create(this.campaignQueryService.search(pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/for-save-adgroup")
    public PageDto<CampaignDto.Response.ForSaveAdGroup> searchForSaveAdGroup(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name) {
        return PageDto.create(this.campaignQueryService.searchForSaveAdGroup(pageable, name));
    }
}
