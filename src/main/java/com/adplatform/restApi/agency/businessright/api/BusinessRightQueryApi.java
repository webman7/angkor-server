package com.adplatform.restApi.agency.businessright.api;

import com.adplatform.restApi.advertiser.company.dao.CompanyRepository;
import com.adplatform.restApi.advertiser.company.dto.CompanyDto;
import com.adplatform.restApi.advertiser.company.dto.CompanyMapper;
import com.adplatform.restApi.advertiser.company.service.CompanyFindUtils;
import com.adplatform.restApi.advertiser.user.dao.UserRepository;
import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.user.service.UserFindUtils;
import com.adplatform.restApi.advertiser.wallet.dto.WalletDto;
import com.adplatform.restApi.agency.businessright.dao.BusinessRightRequestRepository;
import com.adplatform.restApi.agency.businessright.dao.mapper.BusinessRightQueryMapper;
import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agency/businessright")
public class BusinessRightQueryApi {

    private final BusinessRightQueryMapper businessRightQueryMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final BusinessRightRequestRepository businessRightRequestRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/statistics")
    public BusinessRightDto.Response.Statistics statistics(@PathVariable Integer id, BusinessRightDto.Request.Statistics request) {
        return this.businessRightQueryMapper.statistics(request, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/search")
    public PageDto<BusinessRightDto.Response.Search> search(
            @PathVariable Integer id,
            @PageableDefault Pageable pageable,
            BusinessRightDto.Request.Search request) {

        request.setCompanyId(id);
        return PageDto.create(this.businessRightRequestRepository.search(pageable, request));
    }
}
