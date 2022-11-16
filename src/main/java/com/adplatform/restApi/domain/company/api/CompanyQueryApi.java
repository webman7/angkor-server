package com.adplatform.restApi.domain.company.api;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
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
    public PageDto<CompanyDto.Response.Default> search(
            @PageableDefault Pageable pageable,
            CompanyDto.Request.Search searchRequest) {
        return PageDto.create(this.companyRepository.search(pageable, searchRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/for-signup")
    public List<CompanyDto.Response.Default> searchForSignUp(
            @RequestParam Company.Type type,
            @RequestParam(required = false) String name) {
        return this.companyRepository.searchForSignUp(type, name);
    }
}
