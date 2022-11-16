package com.adplatform.restApi.domain.creative.api;

import com.adplatform.restApi.domain.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class CreativeQueryApi {
    private final CreativeRepository creativeRepository;

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<CreativeDto.Response.Default> search(
            @Valid CreativeDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.creativeRepository.search(request, pageable));
    }
}
