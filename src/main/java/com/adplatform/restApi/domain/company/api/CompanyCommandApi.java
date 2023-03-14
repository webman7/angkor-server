package com.adplatform.restApi.domain.company.api;

import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserDto;
import com.adplatform.restApi.domain.company.service.CompanyService;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyCommandApi {
    private final CompanyService companyService;

//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("/advertisers")
//    public void saveAdvertiser(@RequestBody @Valid CompanyDto.Request.Save request) {
//        this.companyService.saveAdvertiser(request);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("/agencies")
//    public void saveAgency(@RequestBody @Valid CompanyDto.Request.Save request) {
//        this.companyService.saveAgency(request);
//    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/media")
    public void saveMedia(@RequestBody @Valid CompanyDto.Request.Save request) {
        this.companyService.saveMedia(request);
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody @Valid CompanyDto.Request.Update request) {
        this.companyService.update(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.companyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user")
    public void saveUser(@RequestBody @Valid MediaCompanyUserDto.Request.SaveUser request) {
        this.companyService.saveUser(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user/invite")
    public void saveUserInvite(@RequestBody @Valid MediaCompanyUserDto.Request.SaveUser request) {
        this.companyService.saveUserInvite(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/member")
    public void updateUserMember(@RequestBody @Valid MediaCompanyUserDto.Request.UserMemberUpdate request) {
        this.companyService.updateUserMember(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/status")
    public void updateUserStatus(@RequestBody @Valid MediaCompanyUserDto.Request.UserStatusUpdate request) {
        this.companyService.updateUserStatus(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/accounting")
    public void updateUserAccounting(@RequestBody @Valid MediaCompanyUserDto.Request.UserUpdate request) {
        this.companyService.updateUserAccounting(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user")
    public void deleteUser(@RequestBody @Valid MediaCompanyUserDto.Request.UserUpdate request) {
        this.companyService.deleteUser(request, SecurityUtils.getLoginUserNo());
    }
}
