package com.adplatform.restApi.domain.company.api;

import com.adplatform.restApi.domain.company.domain.CompanyFile;
import com.adplatform.restApi.domain.company.dto.CompanyFileDto;
import com.adplatform.restApi.domain.company.exception.CompanyNotFoundException;
import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.dao.user.MediaCompanyUserRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserDto;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyQueryApi {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final MediaCompanyUserRepository mediaCompanyUserRepository;

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}")
//    public CompanyDto.Response.Detail findById(@PathVariable Integer id) {
//        Company company = this.companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
//        List<CompanyFileDto.Response.Default> companyFile = this.companyRepository.findDetailBusinessFilesById(id);
//
//
//        return this.companyMapper.toDetailResponse(CompanyFindUtils.findByIdOrElseThrow(id, this.companyRepository), this.companyRepository.findDetailFilesById(id));
//    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<CompanyDto.Response.Default> list() {
        return this.companyRepository.list();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/media/list")
    public List<CompanyDto.Response.MediaByCompany> listMediaByCompany(CompanyDto.Request.MediaByCompany searchRequest) {
        Company company = this.companyRepository.findById(searchRequest.getCompanyId()).orElseThrow(CompanyNotFoundException::new);

        return this.companyRepository.listMediaByCompany(searchRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/media/{id}")
    public CompanyDto.Response.MediaDetail findById(@PathVariable Integer id) {
        Company company = this.companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);

        return this.companyMapper.toMediaDetailResponse(CompanyFindUtils.findByIdOrElseThrow(id, this.companyRepository),
                this.companyRepository.findDetailBusinessFilesById(id),
                this.companyRepository.findDetailBankFilesById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/media/search")
    public PageDto<CompanyDto.Response.CompanyInfo> searchMedia(
            @PageableDefault Pageable pageable,
            CompanyDto.Request.SearchMedia searchRequest) {
        return PageDto.create(this.companyRepository.searchMedia(pageable, searchRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/users")
    public PageDto<MediaCompanyUserDto.Response.MediaCompanyUserInfo> mediaCompanyUserInfo(
            @PageableDefault Pageable pageable,
            @PathVariable(name = "id") Integer companyId) {
        return PageDto.create(this.mediaCompanyUserRepository.mediaCompanyUserInfo(pageable, companyId));
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}/users")
//    public List<MediaCompanyUserDto.Response.MediaCompanyUserInfo> mediaCompanyUserInfo(@PathVariable(name = "id") Integer companyId) {
//        return this.mediaCompanyUserRepository.mediaCompanyUserInfo(companyId);
//    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/users/request")
    public List<MediaCompanyUserDto.Response.MediaCompanyUserInfo> mediaCompanyRequestUserInfo(@PathVariable(name = "id") Integer companyId) {
        return this.mediaCompanyUserRepository.mediaCompanyRequestUserInfo(companyId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/users/master")
    public List<MediaCompanyUserDto.Response.MediaCompanyUserInfo> mediaCompanyMasterUserInfo(@PathVariable(name = "id") Integer companyId) {
        return this.mediaCompanyUserRepository.mediaCompanyMasterUserInfo(companyId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/user/{userNo}")
    public MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo(@PathVariable(name = "id") Integer companyId, @PathVariable(name = "userNo") Integer userNo) {
        return this.mediaCompanyUserRepository.mediaCompanyUserInfo(companyId, userNo);
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/registration/number")
    public PageDto<CompanyDto.Response.AdAccountDetail> registrationNumber(
            @PageableDefault Pageable pageable,
            CompanyDto.Request.SearchKeyword searchRequest) {
        return PageDto.create(this.companyRepository.registrationNumber(pageable, searchRequest));
    }
}
