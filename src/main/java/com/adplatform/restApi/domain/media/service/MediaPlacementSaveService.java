package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.history.domain.business.user.BusinessAccountUserInfoHistory;
import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.dao.placement.MediaPlacementRepository;
import com.adplatform.restApi.domain.media.dao.placement.PlacementRepository;
import com.adplatform.restApi.domain.media.domain.*;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementMapper;
import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;
import com.adplatform.restApi.domain.media.dto.placement.PlacementMapper;
import com.adplatform.restApi.domain.media.exception.PlacementSizeAlreadyExistException;
import com.adplatform.restApi.domain.media.exception.PlacementUpdateException;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class MediaPlacementSaveService {
    private final MediaRepository mediaRepository;
    private final PlacementRepository placementRepository;
    private final MediaPlacementRepository mediaPlacementRepository;
    private final MediaPlacementMapper mediaPlacementMapper;
    private final PlacementMapper placementMapper;
    private final FileService fileService;

    public void save(MediaPlacementDto.Request.Save request) {
        Media media = MediaFindUtils.findByIdOrElseThrow(request.getMediaId(), this.mediaRepository);
        Placement placement = PlacementFindUtils.findByIdOrElseThrow((request.getPlacementId()), this.placementRepository);
        MediaPlacement mediaPlacement = this.mediaPlacementMapper.toEntity(request, media, placement);

        if(request.getMediaPlacementFiles().size() > 0) {
            request.getMediaPlacementFiles().forEach(file -> mediaPlacement.addMediaPlacementFile(this.saveMediaPlacementFile(request, mediaPlacement, file)));
        }

        this.mediaPlacementRepository.save(mediaPlacement);
    }

    public void update(MediaPlacementDto.Request.Update request) {
        try{
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getMediaId(), this.mediaRepository);
            Placement placement = PlacementFindUtils.findByIdOrElseThrow((request.getPlacementId()), this.placementRepository);

            MediaPlacement mediaPlacement = MediaPlacementFindUtils.findByIdOrElseThrow(request.getId(), this.mediaPlacementRepository).update(request);
            if(request.getMediaPlacementFiles().size() > 0) {
                request.getMediaPlacementFiles().forEach(file -> mediaPlacement.addMediaPlacementFile(this.saveMediaPlacementFile(request, mediaPlacement, file)));
            }
        }catch (Exception e){
            throw new PlacementUpdateException();
        }
    }

    public void updateReRegister(MediaPlacementDto.Request.Update request) {
        update(request);
        MediaPlacementFindUtils.findByIdOrElseThrow(request.getId(), this.mediaPlacementRepository).changeStatusN();
    }

    public void delete(Integer id) {
        MediaPlacementFindUtils.findByIdOrElseThrow(id, this.mediaPlacementRepository).delete();
    }

    public void updateAdminApprove(MediaPlacementDto.Request.Update request) {
        MediaPlacement mediaPlacement = MediaPlacementFindUtils.findByIdOrElseThrow(request.getId(), this.mediaPlacementRepository);

        Integer count = this.placementRepository.findByWidthAndHeight(mediaPlacement.getWidth(), mediaPlacement.getHeight());

        if(count.equals(0)) {
            PlacementDto.Request.Save pData = new PlacementDto.Request.Save();
            pData.setName(mediaPlacement.getWidth() + "X" + mediaPlacement.getHeight());
            pData.setWidth(mediaPlacement.getWidth());
            pData.setHeight(mediaPlacement.getHeight());
            pData.setWidthHeightRate(mediaPlacement.getWidthHeightRate());
            pData.setMemo(mediaPlacement.getMemo());
            pData.setAdminMemo(request.getAdminMemo());

            Placement placement = this.placementMapper.toEntity(pData);
            Integer placementId = this.placementRepository.save(placement).updateAdminApprove().getId();
            MediaPlacementFindUtils.findByIdOrElseThrow(request.getId(), this.mediaPlacementRepository).updateAdminApproveNew(request, placementId);
        } else {
            MediaPlacementFindUtils.findByIdOrElseThrow(request.getId(), this.mediaPlacementRepository).updateAdminApprove(request);
        }




    }

    public void updateAdminReject(MediaPlacementDto.Request.Update request) {
        MediaPlacement mediaPlacement = MediaPlacementFindUtils.findByIdOrElseThrow(request.getId(), this.mediaPlacementRepository).updateAdminMemo(request);
        mediaPlacement.changeStatusR();
    }

    public void updateAdminDelete(MediaPlacementDto.Request.Update request) {
        MediaPlacement mediaPlacement = MediaPlacementFindUtils.findByIdOrElseThrow(request.getId(), this.mediaPlacementRepository).updateAdminMemo(request);
        mediaPlacement.changeStatusD();
    }

    @SneakyThrows
    private MediaPlacementFile saveMediaPlacementFile(MediaPlacementDto.Request.Save request, MediaPlacement mediaPlacement, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
        String savedFileUrl = this.fileService.saveMediaPlacement(request, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new MediaPlacementFile(mediaPlacement, this.mediaPlacementFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation mediaPlacementFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
        FileInformation.FileType fileType;
        if (mimetype.startsWith("image")) {
            fileType = FileInformation.FileType.IMAGE;
        } else if (mimetype.startsWith("application/pdf")) {
            fileType = FileInformation.FileType.PDF;
        } else {
            throw new UnsupportedOperationException();
        }

        return FileInformation.builder()
                .url(savedFileUrl)
                .fileType(fileType)
                .fileSize(file.getSize())
                .filename(savedFilename)
                .originalFileName(originalFilename)
                .mimeType(mimetype)
                .build();
    }
}
