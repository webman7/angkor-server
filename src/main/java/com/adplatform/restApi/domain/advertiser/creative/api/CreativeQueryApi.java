package com.adplatform.restApi.domain.advertiser.creative.api;

import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.adgroup.service.AdGroupFindUtils;
import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.advertiser.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.advertiser.creative.dao.CreativeMediaCategoryRepository;
import com.adplatform.restApi.domain.advertiser.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.advertiser.creative.dao.mapper.CreativeQueryMapper;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeMediaCategory;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeMapper;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeMediaCategoryMapper;
import com.adplatform.restApi.domain.advertiser.creative.exception.CreativeNotFoundException;
import com.adplatform.restApi.domain.advertiser.creative.service.CreativeMediaCategoryFindUtils;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCreativeId;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroup.adGroup;

/**
 * @author junny
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
    private final CreativeMediaCategoryMapper creativeMediaCategoryMapper;
    private final CreativeMediaCategoryRepository creativeMediaCategoryRepository;

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

    @AuthorizedAdAccountByCreativeId
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/category")
    public List<CreativeDto.Response.Category> creativeCategory(@PathVariable(name = "id") Integer creativeId) {
        Creative creative = this.creativeRepository.findDetailById(creativeId).orElseThrow(CreativeNotFoundException::new);

        return this.creativeQueryMapper.creativeMediaCategoryList(creativeId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/review/search")
    public PageDto<CreativeDto.Response.ReviewSearch> reviewSearch(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid CreativeDto.Request.ReviewSearch request) {

        return PageDto.create(new PageImpl<>(
                this.creativeQueryMapper.reviewSearch(pageable, request, SecurityUtils.getLoginUserNo()),
                pageable,
                this.creativeQueryMapper.countReviewSearch(request, SecurityUtils.getLoginUserNo())));
    }
}
