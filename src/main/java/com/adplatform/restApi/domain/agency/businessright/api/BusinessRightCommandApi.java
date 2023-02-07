package com.adplatform.restApi.domain.agency.businessright.api;

import com.adplatform.restApi.domain.agency.businessright.dto.BusinessRightDto;
import com.adplatform.restApi.domain.agency.businessright.service.BusinessRightService;
import com.adplatform.restApi.domain.user.dao.UserRepository;
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
