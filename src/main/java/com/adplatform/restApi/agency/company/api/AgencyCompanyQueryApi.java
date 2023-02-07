package com.adplatform.restApi.agency.company.api;

import com.adplatform.restApi.user.dao.UserRepository;
import com.adplatform.restApi.user.domain.User;
import com.adplatform.restApi.user.service.UserFindUtils;
import com.adplatform.restApi.agency.company.dao.AgencyCompanyRepository;
import com.adplatform.restApi.agency.company.dto.AgencyCompanyDto;
import com.adplatform.restApi.agency.company.dao.mapper.AgencyCompanyQueryMapper;
import com.adplatform.restApi.agency.company.dto.AgencyCompanyMapper;
import com.adplatform.restApi.agency.company.service.AgencyCompanyFindUtils;
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
    private final AgencyCompanyMapper agencyCompanyMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public AgencyCompanyDto.Response.Detail my() {
        User user = UserFindUtils.findByIdOrElseThrow(SecurityUtils.getLoginUserNo(), this.userRepository);
        return this.agencyCompanyMapper.toDetailResponse(AgencyCompanyFindUtils.findByIdOrElseThrow(user.getCompany().getId(), this.agencyCompanyRepository), user.getId(), user.getName(), user.getLoginId());
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
