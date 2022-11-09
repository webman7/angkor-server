package com.adplatform.restApi.domain.adgroup.api;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/adgroups/search")
public class AdGroupQueryApi {
    private final AdGroupRepository adGroupRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PageDto<AdGroupDto.Response.Default> search(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer campaignId) {
        return PageDto.create(this.adGroupRepository.search(pageable, campaignId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/for-save-creative")
    public PageDto<AdGroupDto.Response.ForSaveCreative> searchForSaveCreative(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name) {
        return PageDto.create(this.adGroupRepository.searchForSaveCreative(pageable, name));
    }
}
