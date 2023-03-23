package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.media.dao.placement.PlacementRepository;
import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.domain.FileInformation;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;
import com.adplatform.restApi.domain.media.dto.placement.PlacementMapper;
import com.adplatform.restApi.domain.media.exception.PlacementNotFoundException;
import com.adplatform.restApi.domain.media.service.MediaFindUtils;
import com.adplatform.restApi.domain.media.service.PlacementFindUtils;
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
    private final PlacementMapper placementMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<PlacementDto.Response.Search> search(
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.placementRepository.search(pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<PlacementDto.Response.Search> list() {
        return this.placementRepository.list();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PlacementDto.Response.PlacementInfo findById(@PathVariable Integer id) {
        Placement placement = this.placementRepository.findById(id).orElseThrow(PlacementNotFoundException::new);

        return this.placementMapper.toResponse(PlacementFindUtils.findByIdOrElseThrow(id, this.placementRepository));
    }
}
