package com.adplatform.restApi.domain.agency.taxbill.api;

import com.adplatform.restApi.domain.agency.settlement.dao.mapper.AgencySettlementQueryMapper;
import com.adplatform.restApi.domain.agency.settlement.dto.AgencySettlementDto;
import com.adplatform.restApi.domain.agency.taxbill.dao.mapper.AgencyTaxBillQueryMapper;
import com.adplatform.restApi.domain.agency.taxbill.dto.AgencyTaxBillDto;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.service.UserFindUtils;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agency/taxbill")
public class AgencyTaxBillQueryApi {
    private final UserRepository userRepository;
    private final AgencyTaxBillQueryMapper agencyTaxBillQueryMapper;

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/search")
//    public PageDto<AgencyTaxBillDto.Response.Search> search(
//            @PageableDefault Pageable pageable,
//            AgencyTaxBillDto.Request.Search request) {
//
//        User user = UserFindUtils.findByIdOrElseThrow(SecurityUtils.getLoginUserNo(), this.userRepository);
//        request.setCompanyId(user.getCompany().getId());
//
//        return PageDto.create(new PageImpl<>(
//                this.agencyTaxBillQueryMapper.search(request, pageable),
//                pageable,
//                this.agencyTaxBillQueryMapper.countSearch(request)));
//    }
}
