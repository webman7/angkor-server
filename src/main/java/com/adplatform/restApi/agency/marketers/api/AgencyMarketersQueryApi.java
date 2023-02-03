package com.adplatform.restApi.agency.marketers.api;

import com.adplatform.restApi.advertiser.user.dao.UserRepository;
import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.user.service.UserFindUtils;
import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import com.adplatform.restApi.agency.company.dto.AgencyCompanyDto;
import com.adplatform.restApi.agency.company.service.AgencyCompanyFindUtils;
import com.adplatform.restApi.agency.marketers.dao.mapper.AgencyMarketersQueryMapper;
import com.adplatform.restApi.agency.marketers.dto.AgencyMarketersDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agency/marketers")
public class AgencyMarketersQueryApi {
    private final AgencyMarketersQueryMapper agencyMarketersQueryMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/company/my")
    public List<AgencyMarketersDto.Response.MarketersDetail> my() {
        return this.agencyMarketersQueryMapper.my(SecurityUtils.getLoginUserNo());
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}")
//    public AgencyMarketersDto.Response.Marketers marketer(@PathVariable Integer id) {
//        return this.agencyMarketersQueryMapper.marketer(id);
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/businessright")
    public PageDto<AgencyMarketersDto.Response.Search> search(
            @PathVariable Integer id,
            @PageableDefault Pageable pageable,
            AgencyMarketersDto.Request.Search request) {

        request.setUserNo(id);

        return PageDto.create(new PageImpl<>(
                this.agencyMarketersQueryMapper.search(request, pageable),
                pageable,
                this.agencyMarketersQueryMapper.countSearch(request)));
    }

}
