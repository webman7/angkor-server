package com.adplatform.restApi.agency.marketers.api;

import com.adplatform.restApi.user.dto.user.UserDto;
import com.adplatform.restApi.agency.marketers.dto.AgencyMarketersDto;
import com.adplatform.restApi.agency.marketers.service.AgencyMarketersService;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agency/marketers")
public class AgencyMarketersCommandApi {

    private final AgencyMarketersService agencyMarketersService;
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/status")
    public void updateUserStatus(@RequestBody @Valid UserDto.Request.UpdateStatus request) {
        this.agencyMarketersService.updateUserStatus(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/roles")
    public void updateUserRoles(@PathVariable Integer id, AgencyMarketersDto.Request.UpdateAgencyRoles request) {
        this.agencyMarketersService.updateUserRoles(request, id, SecurityUtils.getLoginUserNo());
    }
}
