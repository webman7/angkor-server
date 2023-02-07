package com.adplatform.restApi.placement.api;

import com.adplatform.restApi.placement.dao.PlacementRepository;
import com.adplatform.restApi.placement.dto.placement.PlacementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/placements")
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
