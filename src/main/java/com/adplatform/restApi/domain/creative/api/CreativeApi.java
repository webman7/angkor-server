package com.adplatform.restApi.domain.creative.api;

import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.creative.service.CreativeSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/creative")
public class CreativeApi {
    private final CreativeSaveService creativeSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@Valid CreativeDto.Request.Save request) {
        this.creativeSaveService.save(request);
    }
}
