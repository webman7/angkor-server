package com.adplatform.restApi.domain.advertiser.adgroup.api;

import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupMediaRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.mapper.AdGroupQueryMapper;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupMapper;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupMediaDto;
import com.adplatform.restApi.domain.advertiser.adgroup.exception.AdGroupNotFoundException;
import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.advertiser.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByAdGroupId;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/adgroups")
public class AdGroupQueryApi {

    private final CampaignRepository campaignRepository;
    private final AdGroupRepository adGroupRepository;
    private final AdGroupMediaRepository adGroupMediaRepository;
    private final AdGroupQueryMapper adGroupQueryMapper;
    private final AdGroupMapper adGroupMapper;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    public PageDto<AdGroupDto.Response.AdvertiserSearch> search(
            @RequestBody @Valid AdvertiserSearchRequest request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.adGroupQueryMapper.search(request, pageable),
                pageable,
                this.adGroupQueryMapper.countSearch(request)
        ));
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/for-save-creative")
    public PageDto<AdGroupDto.Response.ForSaveCreative> searchForSaveCreative(
            @Valid AdGroupDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.adGroupRepository.searchForSaveCreative(request, pageable));
    }

    @AuthorizedAdAccountByAdGroupId
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AdGroupDto.Response.Detail findByIdForUpdate(@PathVariable(name = "id") Integer adGroupId) {
        AdGroup adGroup = this.adGroupRepository.findByIdFetchJoin(adGroupId)
                .orElseThrow(AdGroupNotFoundException::new);
        return this.adGroupMapper.toDetailResponse(adGroup);
    }

    @AuthorizedAdAccountByAdGroupId
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/media")
    public List<AdGroupMediaDto.Response.Default> findAdGroupMediaById(@PathVariable(name = "id") Integer adGroupId) {
        return this.adGroupMediaRepository.toAdGroupMedia(adGroupId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/placement")
    public List<AdGroupDto.Response.Placement> adGroupPlacementList(@PathVariable(name = "id") Integer adGroupId) {
        return this.adGroupQueryMapper.adGroupPlacementList(adGroupId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/category")
    public List<AdGroupDto.Response.Category> adGroupCategoryList(@PathVariable(name = "id") Integer adGroupId) {
        return this.adGroupQueryMapper.adGroupCategoryList(adGroupId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/category/media")
    public List<AdGroupDto.Response.CategoryMedia> adGroupCategoryMediaList(@PathVariable(name = "id") Integer adGroupId) {
        return this.adGroupQueryMapper.adGroupCategoryMediaList(adGroupId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/placement/category")
    public List<AdGroupDto.Response.PlacementCategory> adGroupPlacementCategoryList(@PathVariable(name = "id") Integer adGroupId,
                                                                              @Valid AdGroupDto.Request.PlacementCategory request) {
        return this.adGroupQueryMapper.adGroupPlacementCategoryList(adGroupId, request.getPlacementId());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/placement/media")
    public List<AdGroupDto.Response.PlacementMedia> adGroupPlacementMediaList(@PathVariable(name = "id") Integer adGroupId,
                @Valid AdGroupDto.Request.PlacementMedia request) {
        return this.adGroupQueryMapper.adGroupPlacementMediaList(adGroupId, request.getPlacementId(), request.getCategoryId());
    }
}
