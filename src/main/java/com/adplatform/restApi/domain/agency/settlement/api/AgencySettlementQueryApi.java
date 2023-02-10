package com.adplatform.restApi.domain.agency.settlement.api;

import com.adplatform.restApi.domain.agency.settlement.dao.mapper.AgencySettlementQueryMapper;
import com.adplatform.restApi.domain.agency.settlement.dto.AgencySettlementDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agency/settlement")
public class AgencySettlementQueryApi {

    private final UserRepository userRepository;
    private final AgencySettlementQueryMapper agencySettlementQueryMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/amountSum")
    public AgencySettlementDto.Response.AmountSum amountSum(AgencySettlementDto.Request.AmountSum request) {
        User user = UserFindUtils.findByIdOrElseThrow(SecurityUtils.getLoginUserNo(), this.userRepository);
        request.setCompanyId(user.getCompany().getId());
        return this.agencySettlementQueryMapper.amountSum(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<AgencySettlementDto.Response.Search> search(
            @PageableDefault Pageable pageable,
            AgencySettlementDto.Request.Search request) {

        User user = UserFindUtils.findByIdOrElseThrow(SecurityUtils.getLoginUserNo(), this.userRepository);
        request.setCompanyId(user.getCompany().getId());

        return PageDto.create(new PageImpl<>(
                this.agencySettlementQueryMapper.search(request, pageable),
                pageable,
                this.agencySettlementQueryMapper.countSearch(request)));
    }
}
