package com.adplatform.restApi.domain.company.api;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyQueryApi {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CompanyDto.Response.Detail findById(@PathVariable Integer id) {
        return this.companyMapper.toDetailResponse(CompanyFindUtils.findById(id, this.companyRepository));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<CompanyDto.Response.Page> search(
            @PageableDefault Pageable pageable,
            CompanyDto.Request.Search searchRequest) {
        return PageDto.create(this.companyRepository.search(pageable, searchRequest));
    }
}
