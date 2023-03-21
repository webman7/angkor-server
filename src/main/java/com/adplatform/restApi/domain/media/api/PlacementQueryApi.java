package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.media.dao.placement.PlacementRepository;
import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/placement")
public class PlacementQueryApi {

    private final PlacementRepository placementRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<PlacementDto.Response.Search> search(
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.placementRepository.search(pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<PlacementDto.Response.ForSearchAll> searchForAll(
            @RequestParam(required = false) Integer adGroupId,
            @RequestParam(required = false) Integer mediaId
    ) {
        return this.placementRepository.searchForAll(adGroupId, mediaId);
    }

}
