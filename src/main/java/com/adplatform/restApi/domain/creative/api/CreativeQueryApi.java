package com.adplatform.restApi.domain.creative.api;

import com.adplatform.restApi.domain.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<CreativeDto.Response.Default> search(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name) {
        return this.creativeRepository.search(pageable, name);
    }
}
