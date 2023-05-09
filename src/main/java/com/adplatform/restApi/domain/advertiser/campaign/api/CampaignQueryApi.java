package com.adplatform.restApi.domain.advertiser.campaign.api;

import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.mapper.CampaignQueryMapper;
import com.adplatform.restApi.domain.advertiser.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.advertiser.campaign.dto.BudgetDto;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCampaignId;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class CampaignQueryApi {
    private final CampaignRepository campaignRepository;
    private final CampaignQueryMapper campaignQueryMapper;

    private final AdGroupRepository adGroupRepository;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    public PageDto<CampaignDto.Response.Page> search(
            @RequestBody @Valid AdvertiserSearchRequest request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.campaignQueryMapper.search(request, pageable),
                pageable,
                this.campaignQueryMapper.countSearch(request)));
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/for-save-adgroup")
    public PageDto<CampaignDto.Response.ForSaveAdGroup> searchForSaveAdGroup(
            @Valid CampaignDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.campaignRepository.searchForSaveAdGroup(request, pageable));
    }

    @AuthorizedAdAccountByCampaignId
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/update-for-detail")
    public CampaignDto.Response.ForUpdate searchForUpdate(@PathVariable(name = "id") Integer campaignId) {
        return this.campaignRepository.searchForUpdate(campaignId);
    }

    @AuthorizedAdAccountByCampaignId
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/budget")
    public BudgetDto getBudget(@PathVariable(name = "id") Integer campaignId) {
        return BudgetDto.create(this.campaignRepository.getBudget(campaignId), this.adGroupRepository.getBudget(campaignId));
    }

    @AuthorizedAdAccountByCampaignId
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CampaignDto.Response.Detail searchByCampaignId(@PathVariable(name = "id") Integer campaignId) {
        return this.campaignQueryMapper.searchByCampaignId(campaignId);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search-admin")
    public PageDto<CampaignDto.Response.Page> searchAdmin(
            @RequestBody @Valid AdvertiserSearchRequest request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.campaignQueryMapper.search(request, pageable),
                pageable,
                this.campaignQueryMapper.countSearch(request)));
    }

}
