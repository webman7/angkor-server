package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserDto;
import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import com.adplatform.restApi.domain.media.service.CategorySaveService;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByAdGroupId;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryCommandApi {

    CategorySaveService categorySaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@RequestBody @Valid CategoryDto.Request.Save request) {
        this.categorySaveService.save(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void update(@RequestBody @Valid CategoryDto.Request.Update request) {
        this.categorySaveService.update(request);
    }

    @AuthorizedAdAccountByAdGroupId
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.categorySaveService.delete(id);
    }
}
