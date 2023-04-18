package com.adplatform.restApi.domain.statistics.service;

import com.adplatform.restApi.domain.bank.dao.BankRepository;
import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.bank.service.BankFindUtils;
import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.exception.MediaUpdateException;
import com.adplatform.restApi.domain.media.service.MediaFindUtils;
import com.adplatform.restApi.domain.statistics.dao.taxbill.MediaTaxBillRepository;
import com.adplatform.restApi.domain.statistics.domain.taxbill.FileInformation;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.MediaFile;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBill;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBillFile;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBillPaymentFile;
import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import com.adplatform.restApi.domain.statistics.dto.TaxBillMapper;
import com.adplatform.restApi.domain.statistics.exception.MediaTaxBillAlreadyExistException;
import com.adplatform.restApi.domain.statistics.exception.MediaTaxBillUpdateException;
import com.adplatform.restApi.global.util.CommonUtils;
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
    private final MediaTaxBillRepository mediaTaxBillRepository;
    private final TaxBillMapper taxBillMapper;
    private final AwsFileService awsFileService;
    public void saveMediaTaxBill(TaxBillDto.Request.Save request) {
        try {
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getMediaId(), this.mediaRepository);
            // 해당 월의 마지막 날짜로 세팅한다.
            request.setStatDate(Integer.parseInt(CommonUtils.getLastDayOfMonthByYMD(request.getStatDate().toString())));

            MediaTaxBill mediaTaxBill = this.taxBillMapper.toEntityMediaTaxBill(request);

            if(request.getMediaTaxBillFiles().size() > 0) {
                request.getMediaTaxBillFiles().forEach(file -> mediaTaxBill.addMediaTaxBillFile(this.saveMediaTaxBillFile(request, mediaTaxBill, file)));
            }
            this.mediaTaxBillRepository.save(mediaTaxBill);
        } catch (Exception e) {
            throw new MediaTaxBillAlreadyExistException();
        }
    }

    public void updateMediaTaxBill(TaxBillDto.Request.Update request) {
        try{
            MediaTaxBill mediaTaxBill = MediaTaxBillFindUtils.findByIdOrElseThrow(request.getId(), this.mediaTaxBillRepository);
            if(!mediaTaxBill.isIssueStatus()) {
                mediaTaxBill.update(request);
                if(request.getMediaTaxBillFiles().size() > 0) {
                    request.getMediaTaxBillFiles().forEach(file -> mediaTaxBill.addMediaTaxBillFile(this.saveMediaTaxBillFile(request, mediaTaxBill, file)));
                }
            }
        }catch (Exception e){
            throw new MediaTaxBillUpdateException();
        }
    }

    public void updateMediaTaxBillIssue(TaxBillDto.Request.Confirm request) {
        try{
            MediaTaxBill mediaTaxBill = MediaTaxBillFindUtils.findByIdOrElseThrow(request.getId(), this.mediaTaxBillRepository);
            if(!mediaTaxBill.isIssueStatus()) {
                mediaTaxBill.updateIssue();
            }
        }catch (Exception e){
            throw new MediaTaxBillUpdateException();
        }
    }

    public void updateMediaTaxBillPayment(TaxBillDto.Request.Payment request) {
        try{
            MediaTaxBill mediaTaxBill = MediaTaxBillFindUtils.findByIdOrElseThrow(request.getId(), this.mediaTaxBillRepository);
            if(!mediaTaxBill.isIssueStatus()) {
                throw new MediaTaxBillUpdateException();
            }
            if(!mediaTaxBill.isPaymentStatus()) {
                mediaTaxBill.updatePayment(request);
                if(request.getMediaTaxBillPaymentFiles().size() > 0) {
                    request.getMediaTaxBillPaymentFiles().forEach(file -> mediaTaxBill.addMediaTaxBillPaymentFile(this.saveMediaTaxBillPaymentFile(request, mediaTaxBill, file)));
                }
            }
        }catch (Exception e){
            throw new MediaTaxBillUpdateException();
        }
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
    private MediaTaxBillPaymentFile saveMediaTaxBillPaymentFile(TaxBillDto.Request.Payment request, MediaTaxBill mediaTaxBill, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
        String savedFileUrl = this.awsFileService.saveMediaTaxBillPayment(request, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new MediaTaxBillPaymentFile(mediaTaxBill, this.mediaTaxBillFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
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
