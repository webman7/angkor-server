package com.adplatform.restApi.domain.campaign.api;

import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.service.CampaignQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<CampaignDto.Response.Page> search(@PageableDefault Pageable pageable) {
        return this.campaignQueryService.search(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/for-save-adgroup")
    public Page<CampaignDto.Response.ForSaveAdGroup> searchForSaveAdGroup(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name) {
        return this.campaignQueryService.searchForSaveAdGroup(pageable, name);
    }
}
