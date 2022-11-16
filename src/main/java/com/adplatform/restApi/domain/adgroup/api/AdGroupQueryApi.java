package com.adplatform.restApi.domain.adgroup.api;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupMapper;
import com.adplatform.restApi.domain.adgroup.exception.AdGroupNotFoundException;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/adgroups")
public class AdGroupQueryApi {
    private final AdGroupRepository adGroupRepository;
    private final AdGroupMapper adGroupMapper;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<AdGroupDto.Response.Default> search(
            @Valid AdGroupDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.adGroupRepository.search(request, pageable));
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/for-save-creative")
    public PageDto<AdGroupDto.Response.ForSaveCreative> searchForSaveCreative(
            @Valid AdGroupDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.adGroupRepository.searchForSaveCreative(request, pageable));
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public AdGroupDto.Response.Detail findByIdForUpdate(@PathVariable(name = "id") Integer adGroupId) {
        AdGroup adGroup = this.adGroupRepository.findByIdFetchJoin(adGroupId)
                .orElseThrow(AdGroupNotFoundException::new);
        return this.adGroupMapper.toDetailResponse(adGroup);
    }
}
