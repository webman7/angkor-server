
package com.adplatform.restApi.domain.advertiser.creative.service;

import com.adplatform.restApi.domain.advertiser.creative.dao.CreativeMediaCategoryRepository;
import com.adplatform.restApi.domain.advertiser.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.advertiser.creative.dao.CreativePlacementRepository;
import com.adplatform.restApi.domain.advertiser.creative.dao.mapper.CreativeQueryMapper;
import com.adplatform.restApi.domain.advertiser.creative.domain.*;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeMapper;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeMediaCategoryMapper;
import com.adplatform.restApi.domain.advertiser.creative.exception.CreativeReviewAlreadyApproveException;
import com.adplatform.restApi.domain.advertiser.creative.exception.CreativeReviewAlreadyRejectException;
import com.adplatform.restApi.domain.advertiser.creative.exception.CreativeUpdateException;
import com.adplatform.restApi.domain.history.dao.AdminStopHistoryRepository;
import com.adplatform.restApi.domain.history.domain.AdminStopHistory;
import com.adplatform.restApi.domain.history.dto.AdminStopHistoryDto;
import com.adplatform.restApi.domain.history.dto.AdminStopHistoryMapper;
import com.adplatform.restApi.domain.media.dao.placement.PlacementRepository;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.domain.media.exception.PlacementNotFoundException;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.infra.file.service.AwsFileService;
import com.adplatform.restApi.infra.file.util.ImageSizeUtils;
import com.adplatform.restApi.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CreativeCommandService {
    private final CreativeRepository creativeRepository;
    private final PlacementRepository placementRepository;
    private final CreativePlacementRepository creativePlacementRepository;
    private final FileService fileService;
    private final CreativeMapper creativeMapper;
    private final CreativeQueryMapper creativeQueryMapper;
    private final CreativeMediaCategoryMapper creativeMediaCategoryMapper;
    private final CreativeMediaCategoryRepository creativeMediaCategoryRepository;
    private final AdminStopHistoryMapper adminStopHistoryMapper;
    private final AdminStopHistoryRepository adminStopHistoryRepository;
    private final AwsFileService awsFileService;

    public void save(CreativeDto.Request.Save request) {
        List<Placement> placement = this.findByPlacementId(request.getPlacements());

        Creative creative = this.creativeMapper.toEntity(request, placement);
        request.getFiles().forEach(file -> creative.addFile(this.saveFile(request, creative, file)));
        request.getOpinionProofFiles().forEach(file -> creative.addOpinionProofFile(this.saveOpinionProofFile(creative, file)));
        Integer creativeId = this.creativeRepository.save(creative).getId();

        // 매체 카테고리 정보 인서트
        String mediaCategoryTmp = request.getMediaCategory();
        String [] mediaCategoryItems = mediaCategoryTmp.split(",");
        for (int i=0; i < mediaCategoryItems.length; i++) {
            String [] mediaCategorySubTmp = mediaCategoryItems[i].split("_");
            this.creativeQueryMapper.insertCreativeMediaCategory(creativeId, Integer.parseInt(mediaCategorySubTmp[0]), Integer.parseInt(mediaCategorySubTmp[1]));
        }
    }

    private List<Placement> findByPlacementId(List<Integer> placementId) {
        return placementId.stream().map(this::findByPlacementIdOrElseThrow).collect(Collectors.toList());
    }

    private Placement findByPlacementIdOrElseThrow(Integer placementId) {
        return this.placementRepository.findById(placementId)
                .orElseThrow(PlacementNotFoundException::new);
    }

    public void update(CreativeDto.Request.Update request) {
        try{
            // creative_placement_info delete
            this.creativePlacementRepository.deleteCreativePlacement(request.getCreativeId());
            List<Placement> placement = this.findByPlacementId(request.getPlacements());
            Creative creative = CreativeFindUtils.findByIdOrElseThrow(request.getCreativeId(), this.creativeRepository)
                    .update(request, placement)
                    .deleteOpinionProofFiles(request.getAdGroupId(), request.getDeleteOpinionProofFilenames(), this.fileService);
            request.getOpinionProofFiles().forEach(file -> creative.addOpinionProofFile(this.saveOpinionProofFile(creative, file)));

            // 매체 카테고리 정보 삭제
            this.creativeMediaCategoryRepository.deleteCreativeMediaCategory(request.getCreativeId());
            // 매체 카테고리 정보 인서트
            String mediaCategoryTmp = request.getMediaCategory();
            String [] mediaCategoryItems = mediaCategoryTmp.split(",");
            for (int i=0; i < mediaCategoryItems.length; i++) {
                String [] mediaCategorySubTmp = mediaCategoryItems[i].split("_");
                this.creativeQueryMapper.insertCreativeMediaCategory(request.getCreativeId(), Integer.parseInt(mediaCategorySubTmp[0]), Integer.parseInt(mediaCategorySubTmp[1]));
            }

        }catch (Exception e){
            throw new CreativeUpdateException();
        }
    }

    @SneakyThrows
    private CreativeFile saveFile(CreativeDto.Request.Save request, Creative creative, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
//        String savedFileUrl = this.fileService.save(request, file);
        String savedFileUrl = this.awsFileService.save(request, file);

        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new CreativeFile(creative, request.getType(), this.createFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private CreativeOpinionProofFile saveOpinionProofFile(Creative creative, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
//        String savedFileUrl = this.fileService.saveProofFile(creative, file);
        String savedFileUrl = this.awsFileService.saveProofFile(creative, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new CreativeOpinionProofFile(creative, this.createFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation createFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
        FileInformation.FileType fileType;
        int width = 0;
        int height = 0;
        if (mimetype.startsWith("image")) {
            fileType = FileInformation.FileType.IMAGE;
            width = ImageSizeUtils.getWidth(file);
            height = ImageSizeUtils.getHeight(file);
        } else if (mimetype.startsWith("video")) {
            fileType = FileInformation.FileType.VIDEO;
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
                .width(width)
                .height(height)
                .mimeType(mimetype)
                .build();
    }

    public void delete(Integer id) {
        CreativeFindUtils.findByIdOrElseThrow(id, this.creativeRepository).delete();
    }

    public void deletePlacement(Integer id) {
        CreativeFindUtils.findByIdOrElseThrow(id, this.creativeRepository).delete();
    }

    public void changeConfig(Integer id, Creative.Config config) {
        Creative creative = CreativeFindUtils.findByIdOrElseThrow(id, this.creativeRepository);
        if (config == Creative.Config.ON) {
            // 승인여부 체크하여 Status 변경
            if(creative.getSystemConfig().equals(Creative.SystemConfig.ON)) {
                creative.changeConfigOn();

                if(creative.getReviewStatus().equals(Creative.ReviewStatus.APPROVED)) {
                    creative.changeStatusOperating();
                } else {
                    creative.changeStatusUnApproved();
                }
            }
        }
        else if (config == Creative.Config.OFF) {
            if(!creative.getSystemConfig().equals(Creative.SystemConfig.ADMIN_STOP)) {
                creative.changeConfigOff();
                creative.changeStatusOff();
            }
        }
    }

    public void changeAdminStop(Integer id, CreativeDto.Request.AdminStop request, Boolean adminStop) {
        Creative creative = CreativeFindUtils.findByIdOrElseThrow(id, this.creativeRepository);
        if (adminStop) {
            // 히스토리 저장
            AdminStopHistoryDto.Request.Save history = new AdminStopHistoryDto.Request.Save();
            history.setType(request.getType());
            history.setStopId(id);
            history.setReason(request.getReason());
            AdminStopHistory adminStopHistory = this.adminStopHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
            this.adminStopHistoryRepository.save(adminStopHistory);

            // 관리자 정지
            creative.changeAdminStopOn();
        }
        else {
            creative.changeAdminStopOff();
        }
    }

    public void changeReviewApprove(Integer id, CreativeDto.Request.ReviewApprove request) {
        Creative creative = CreativeFindUtils.findByIdOrElseThrow(id, this.creativeRepository);

        if(creative.getReviewStatus().equals(Creative.ReviewStatus.WAITING)) {
            creative.changeReviewApprove(request, SecurityUtils.getLoginUserNo());
        } else if (creative.getReviewStatus().equals(Creative.ReviewStatus.REJECTED)) {
            throw new CreativeReviewAlreadyRejectException();
        } else if (creative.getReviewStatus().equals(Creative.ReviewStatus.APPROVED)) {
            throw new CreativeReviewAlreadyApproveException();
        }
    }
}
