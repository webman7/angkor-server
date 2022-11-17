package com.adplatform.restApi.domain.creative.api;

import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.creative.service.CreativeSaveService;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/creatives")
public class CreativeCommandApi {
    private final CreativeSaveService creativeSaveService;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@Valid CreativeDto.Request.Save request) {
        this.creativeSaveService.save(request);
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void update(@Valid CreativeDto.Request.Update request) {
        this.creativeSaveService.update(request);
    }
}
