package com.adplatform.restApi.advertiser.creative.api;

import com.adplatform.restApi.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.advertiser.adgroup.service.AdGroupFindUtils;
import com.adplatform.restApi.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.advertiser.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.advertiser.creative.dao.CreativeRepository;
import com.adplatform.restApi.advertiser.creative.dao.mapper.CreativeQueryMapper;
import com.adplatform.restApi.advertiser.creative.domain.Creative;
import com.adplatform.restApi.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.advertiser.creative.dto.CreativeMapper;
import com.adplatform.restApi.advertiser.creative.exception.CreativeNotFoundException;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCreativeId;
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
@RequestMapping("/creatives")
public class CreativeQueryApi {
    private final CampaignRepository campaignRepository;
    private final AdGroupRepository adGroupRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeQueryMapper creativeQueryMapper;
    private final CreativeMapper creativeMapper;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    public PageDto<CreativeDto.Response.Default> search(
            @RequestBody @Valid AdvertiserSearchRequest request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.creativeQueryMapper.search(request, pageable),
                pageable,
                this.creativeQueryMapper.countSearch(request)
        ));
    }

    @AuthorizedAdAccountByCreativeId
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CreativeDto.Response.Detail findById(@PathVariable(name = "id") Integer creativeId) {
        Creative creative = this.creativeRepository.findDetailById(creativeId).orElseThrow(CreativeNotFoundException::new);
        AdGroup adGroup = AdGroupFindUtils.findByIdOrElseThrow(creative.getAdGroup().getId(), this.adGroupRepository);

        return this.creativeMapper.toDetailResponse(
                this.creativeRepository.findDetailById(creativeId)
                        .orElseThrow(CreativeNotFoundException::new),
                this.creativeRepository.findDetailFilesById(creativeId),
                this.creativeRepository.findDetailOpinionProofById(creativeId), adGroup);
    }
}
