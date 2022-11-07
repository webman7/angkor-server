package com.adplatform.restApi.domain.adgroup.api;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/adgroups")
public class AdGroupQueryApi {
    private final AdGroupRepository adGroupRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public Page<AdGroupDto.Response.Default> search(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer campaignId) {
        return this.adGroupRepository.search(pageable, campaignId);
    }
}
