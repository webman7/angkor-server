package com.adplatform.restApi.domain.company.api;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.service.CompanyQueryService;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyQueryApi {
    private final CompanyQueryService companyQueryService;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto.Response.Detail> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.companyMapper.toDetailResponse(this.companyQueryService.findById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<PageDto<CompanyDto.Response.Page>> search(
            @PageableDefault Pageable pageable,
            CompanyDto.Request.Search searchRequest) {
        System.out.println("api: " + searchRequest.getDeleted());
        return ResponseEntity.ok(PageDto.create(this.companyRepository.search(pageable, searchRequest)));
    }
}
