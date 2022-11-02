package com.adplatform.restApi.domain.campaign.api;

import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.service.CampaignQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campaign")
public class CampaignQueryApi {
    private final CampaignQueryService campaignQueryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public Page<CampaignDto.Response.Page> search(@PageableDefault Pageable pageable) {
        return this.campaignQueryService.search(pageable);
    }
}
