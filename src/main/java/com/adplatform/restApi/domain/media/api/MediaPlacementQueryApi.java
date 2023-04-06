package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.dao.placement.MediaPlacementRepository;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.MediaPlacement;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementFileDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementMapper;
import com.adplatform.restApi.domain.media.exception.MediaNotFoundException;
import com.adplatform.restApi.domain.media.exception.PlacementNotFoundException;
import com.adplatform.restApi.domain.media.service.MediaPlacementFindUtils;
import com.adplatform.restApi.domain.statistics.dto.MediaTaxBillFileDto;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.service.UserFindUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/placement/media")
public class MediaPlacementQueryApi {
    private final MediaPlacementRepository mediaPlacementRepository;
    private final MediaRepository mediaRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final MediaPlacementMapper mediaPlacementMapper;
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<MediaPlacementDto.Response.Search> search(
            @PageableDefault Pageable pageable,
            MediaPlacementDto.Request.Search searchRequest) {
        return PageDto.create(this.mediaPlacementRepository.search(pageable, searchRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MediaPlacementDto.Response.MediaPlacementInfo findById(@PathVariable Integer id) {
        MediaPlacement mediaPlacement = this.mediaPlacementRepository.findById(id).orElseThrow(PlacementNotFoundException::new);

        Media media = this.mediaRepository.findById(mediaPlacement.getMedia().getId()).orElseThrow(MediaNotFoundException::new);
        Company company = CompanyFindUtils.findByIdOrElseThrow(media.getCompany().getId(), this.companyRepository);
        User user = UserFindUtils.findByIdOrElseThrow(mediaPlacement.getCreatedUserNo(), this.userRepository);
        MediaPlacementFileDto.Response.FileInfo mediaPlacementFile = this.mediaPlacementRepository.findByMediaPlacementIdFileInfo(id);

        return this.mediaPlacementMapper.toResponse(MediaPlacementFindUtils.findByIdOrElseThrow(id, this.mediaPlacementRepository), media, company, user, mediaPlacementFile);
    }
}
