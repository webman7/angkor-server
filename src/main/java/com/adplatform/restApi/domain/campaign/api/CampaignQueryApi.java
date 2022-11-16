package com.adplatform.restApi.domain.campaign.api;

import com.adplatform.restApi.domain.campaign.dao.CampaignRepository;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.service.CampaignQueryService;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/campaigns/search")
public class CampaignQueryApi {
    private final CampaignQueryService campaignQueryService;
    private final CampaignRepository campaignRepository;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PageDto<CampaignDto.Response.Page> search(
            @Valid CampaignDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.campaignQueryService.search(request, pageable));
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/for-save-adgroup")
    public PageDto<CampaignDto.Response.ForSaveAdGroup> searchForSaveAdGroup(
            @Valid CampaignDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.campaignRepository.searchForSaveAdGroup(request, pageable));
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/update-for-detail")
    public CampaignDto.Response.ForUpdate searchForUpdate(Integer campaignId) {
        return this.campaignRepository.searchForUpdate(campaignId);
    }
}
