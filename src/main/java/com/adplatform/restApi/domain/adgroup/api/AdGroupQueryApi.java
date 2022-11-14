package com.adplatform.restApi.domain.adgroup.api;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
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
@RequestMapping("/adgroups/search")
public class AdGroupQueryApi {
    private final AdGroupRepository adGroupRepository;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PageDto<AdGroupDto.Response.Default> search(
            @Valid AdGroupDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.adGroupRepository.search(request, pageable));
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/for-save-creative")
    public PageDto<AdGroupDto.Response.ForSaveCreative> searchForSaveCreative(
            @Valid AdGroupDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.adGroupRepository.searchForSaveCreative(request, pageable));
    }
}
