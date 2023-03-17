package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.media.dao.placement.PlacementRepository;
import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/placement")
public class PlacementQueryApi {

    private final PlacementRepository placementRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<PlacementDto.Response.ForSearchAll> searchForAll(
            @RequestParam(required = false) Integer adGroupId,
            @RequestParam(required = false) Integer mediaId
    ) {
        return this.placementRepository.searchForAll(adGroupId, mediaId);
    }

}
