package com.adplatform.restApi.domain.statistics.service;

import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.service.MediaFindUtils;
import com.adplatform.restApi.domain.statistics.domain.taxbill.FileInformation;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.MediaFile;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBill;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBillFile;
import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import com.adplatform.restApi.domain.statistics.dto.TaxBillMapper;
import com.adplatform.restApi.infra.file.service.AwsFileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MediaTaxBillSaveService {
    private final MediaRepository mediaRepository;
    private final TaxBillMapper taxBillMapper;
    private final AwsFileService awsFileService;
    public void saveAdminTax(TaxBillDto.Request.Save request) {
        Media media = MediaFindUtils.findByIdOrElseThrow(request.getMediaId(), this.mediaRepository);
        MediaTaxBill mediaTaxBill = this.taxBillMapper.toEntityMediaTaxBill(request);

        if(request.getMediaTaxBillFiles().size() > 0) {
            request.getMediaTaxBillFiles().forEach(file -> mediaTaxBill.addMediaTaxBillFile(this.saveMediaTaxBillFile(request, mediaTaxBill, file)));
        }

//        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
//        Media media = this.mediaMapper.toEntity(request, company);
//
//        if(request.getMediaFiles().size() > 0) {
//            request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
//        }
//        Integer mediaId = this.mediaRepository.save(media).getId();
//        //관리자 메모 업데이트
//        MediaFindUtils.findByIdOrElseThrow(mediaId, this.mediaRepository).saveAdminMemo(request);
//
//        // 루프 돌면서 인서트
//        List<Category> category = this.findByCategoryId(request.getCategory());
//        for (Category ca: category) {
//            this.mediaCategorySaveQueryMapper.insertMediaCategory(mediaId, ca.getId());
//        }
    }

    public void updateAdminTax(TaxBillDto.Request.Update request) {
//        try{
//            Media media = MediaFindUtils.findByIdOrElseThrow(request.getId(), this.mediaRepository).update(request).updateAdminMemo(request);
//            if(request.getMediaFiles().size() > 0) {
//                request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
//            }
//
//            // 매체 카테고리 삭제
//            this.mediaCategorySaveQueryMapper.deleteMediaCategory(request.getId());
//
//            // 루프 돌면서 인서트
//            List<Category> category = this.findByCategoryId(request.getCategory());
//            for (Category ca: category) {
//                this.mediaCategorySaveQueryMapper.insertMediaCategory(media.getId(), ca.getId());
//            }
//
//        }catch (Exception e){
//            throw new MediaUpdateException();
//        }
    }

    @SneakyThrows
    private MediaTaxBillFile saveMediaTaxBillFile(TaxBillDto.Request.Save request, MediaTaxBill mediaTaxBill, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
        String savedFileUrl = this.awsFileService.saveMediaTaxBill(request, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new MediaTaxBillFile(mediaTaxBill, this.mediaTaxBillFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation mediaTaxBillFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
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
