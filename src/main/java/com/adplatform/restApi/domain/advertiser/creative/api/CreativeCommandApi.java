package com.adplatform.restApi.domain.advertiser.creative.api;

import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.advertiser.creative.service.CreativeCommandService;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCreativeId;
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
    private final CreativeCommandService creativeCommandService;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@Valid CreativeDto.Request.Save request) {
        this.creativeCommandService.save(request);
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void update(@Valid CreativeDto.Request.Update request) {
        this.creativeCommandService.update(request);
    }

    @AuthorizedAdAccountByCreativeId
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.creativeCommandService.delete(id);
    }

    @AuthorizedAdAccountByCreativeId
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/on")
    public void changeConfigOn(@PathVariable Integer id) {
        this.creativeCommandService.changeConfig(id, Creative.Config.ON);
    }

    @AuthorizedAdAccountByCreativeId
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/off")
    public void changeConfigOff(@PathVariable Integer id) {
        this.creativeCommandService.changeConfig(id, Creative.Config.OFF);
    }
}
