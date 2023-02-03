package com.adplatform.restApi.agency.businessright.api;

import com.adplatform.restApi.advertiser.company.dto.CompanyDto;
import com.adplatform.restApi.advertiser.company.service.CompanyFindUtils;
import com.adplatform.restApi.advertiser.user.dao.UserRepository;
import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.user.service.UserFindUtils;
import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import com.adplatform.restApi.agency.businessright.service.BusinessRightService;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agency/businessright")
public class BusinessRightCommandApi {

    private final BusinessRightService businessRightService;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/request")
    public void saveBusinessRightRequest(@RequestBody @Valid BusinessRightDto.Request.SaveRequest request) {
        this.businessRightService.saveBusinessRightRequest(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/request")
    public void updateBusinessRightStatus(@RequestBody @Valid BusinessRightDto.Request.SaveStatus request) {
        this.businessRightService.saveBusinessRightStatus(request);
    }
}
