package com.adplatform.restApi.agency.company.api;

import com.adplatform.restApi.advertiser.company.dao.CompanyRepository;
import com.adplatform.restApi.advertiser.company.dto.CompanyDto;
import com.adplatform.restApi.advertiser.company.dto.CompanyMapper;
import com.adplatform.restApi.advertiser.company.service.CompanyFindUtils;
import com.adplatform.restApi.advertiser.user.dao.UserRepository;
import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.user.service.UserFindUtils;
import com.adplatform.restApi.agency.adaccount.dto.AgencyAdAccountDto;
import com.adplatform.restApi.agency.company.dao.AgencyCompanyRepository;
import com.adplatform.restApi.agency.company.dto.AgencyCompanyDto;
import com.adplatform.restApi.agency.company.dao.mapper.AgencyCompanyQueryMapper;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agency/companies")
public class AgencyCompanyQueryApi {

    private final AgencyCompanyQueryMapper agencyCompanyQueryMapper;
    private final AgencyCompanyRepository agencyCompanyRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public CompanyDto.Response.Detail my() {
        User user = UserFindUtils.findByIdOrElseThrow(SecurityUtils.getLoginUserNo(), this.userRepository);
        return this.companyMapper.toDetailResponse(CompanyFindUtils.findByIdOrElseThrow(user.getCompany().getId(), this.companyRepository));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/walletSpendSummary")
    public AgencyCompanyDto.Response.SpendSummary walletSpendSummary(@PathVariable Integer id) {
        return this.agencyCompanyQueryMapper.walletSpendSummary(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/adaccounts/search")
    public PageDto<AgencyCompanyDto.Response.SearchForAdmin> searchForAdmin(
            @PathVariable Integer id,
            @PageableDefault Pageable pageable,
            AgencyCompanyDto.Request.Search request) {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
        String chkDate = SDF.format(calendar.getTime());
        request.setCurrDate(Integer.parseInt(chkDate));

        return PageDto.create(this.agencyCompanyRepository.searchForAdmin(pageable, request, id));
    }
}
