package com.adplatform.restApi.advertiser.adgroup.api;

import com.adplatform.restApi.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.advertiser.adgroup.service.AdGroupService;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByAdGroupId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/adgroups")
public class AdGroupCommandApi {
    private final AdGroupService adGroupService;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void update(@RequestBody @Valid AdGroupDto.Request.Update request) {
        this.adGroupService.update(request);
    }

    @AuthorizedAdAccountByAdGroupId
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.adGroupService.delete(id);
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/copy")
    public void copy(@RequestBody @Valid AdGroupDto.Request.Copy request) {
        this.adGroupService.copy(request);
    }

    @AuthorizedAdAccountByAdGroupId
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/on")
    public void changeConfigOn(@PathVariable Integer id) {
        this.adGroupService.changeConfig(id, AdGroup.Config.ON);
    }

    @AuthorizedAdAccountByAdGroupId
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/off")
    public void changeConfigOff(@PathVariable Integer id) {
        this.adGroupService.changeConfig(id, AdGroup.Config.OFF);
    }
}
