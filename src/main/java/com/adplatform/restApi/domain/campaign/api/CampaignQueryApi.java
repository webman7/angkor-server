package com.adplatform.restApi.domain.campaign.api;

import com.adplatform.restApi.domain.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.campaign.dao.campaign.mapper.CampaignQueryMapper;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.statistics.dto.ReportDto;
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
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/campaigns")
public class CampaignQueryApi {
    private final CampaignRepository campaignRepository;
    private final CampaignQueryMapper campaignQueryMapper;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    public PageDto<CampaignDto.Response.Page> search(
            @Valid AdvertiserSearchRequest request,
            @PageableDefault Pageable pageable,
            @RequestBody @Valid ReportDto.Request reportRequest) {
        return PageDto.create(new PageImpl<>(
                this.campaignQueryMapper.search(request, pageable, reportRequest),
                pageable,
                this.campaignQueryMapper.countSearch(request, reportRequest)));
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
}
