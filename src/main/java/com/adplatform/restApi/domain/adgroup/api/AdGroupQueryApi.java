package com.adplatform.restApi.domain.adgroup.api;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupMediaRepository;
import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.dao.adgroup.mapper.AdGroupQueryMapper;
import com.adplatform.restApi.domain.adgroup.dao.media.MediaRepository;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.domain.AdGroupMedia;
import com.adplatform.restApi.domain.adgroup.domain.Media;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupMapper;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupMediaDto;
import com.adplatform.restApi.domain.adgroup.dto.media.MediaDto;
import com.adplatform.restApi.domain.adgroup.dto.media.MediaMapper;
import com.adplatform.restApi.domain.adgroup.exception.AdGroupNotFoundException;
import com.adplatform.restApi.domain.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.campaign.service.CampaignFindUtils;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByAdGroupId;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Seohyun Lee
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
}
