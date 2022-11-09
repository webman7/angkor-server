package com.adplatform.restApi.domain.creative.api;

import com.adplatform.restApi.domain.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/creative")
public class CreativeQueryApi {
    private final CreativeRepository creativeRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<CreativeDto.Response.Default> search(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name) {
        return PageDto.create(this.creativeRepository.search(pageable, name));
    }
}
