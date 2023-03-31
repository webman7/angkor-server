package com.adplatform.restApi.domain.wallet.service;

import com.adplatform.restApi.domain.wallet.dao.walletlog.WalletLogRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.domain.wallet.dao.walletrefund.WalletRefundRepository;
import com.adplatform.restApi.domain.wallet.domain.*;
import com.adplatform.restApi.domain.wallet.dao.walletchargelog.WalletChargeLogRepository;
import com.adplatform.restApi.domain.wallet.dto.WalletChargeLogMapper;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.domain.wallet.dto.WalletLogMapper;
import com.adplatform.restApi.domain.wallet.dto.WalletRefundMapper;
import com.adplatform.restApi.domain.wallet.exception.WalletRefundAlreadyException;
import com.adplatform.restApi.domain.wallet.exception.WalletRefundAlreadyRejectException;
import com.adplatform.restApi.domain.wallet.exception.WalletRefundNotFoundException;
import com.adplatform.restApi.infra.file.service.AwsFileService;
import com.adplatform.restApi.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Transactional
@Service
public class WalletSaveService {
    private final WalletChargeLogMapper walletChargeLogMapper;
    private final WalletChargeLogRepository walletChargeLogRepository;
    private final FileService fileService;
    private final WalletMasterRepository walletMasterRepository;
    private final WalletRefundMapper walletRefundMapper;
    private final WalletRefundRepository walletRefundRepository;
    private final WalletLogRepository walletLogRepository;
    private final WalletLogMapper walletLogMapper;
    private final AwsFileService awsFileService;


//    @Data
//    @AllArgsConstructor
//    static class WalletCashDto{ // 노출할 것만
//        private Long amount;
//        private Long availableAmount;
//    }

    public void save(WalletDto.Request.SaveCredit request, Integer loginUserNo) {

        // 충전 로그
        WalletChargeLog walletChargeLog = this.walletChargeLogMapper.toEntity(request, loginUserNo);
        request.getWalletChargeFiles().forEach(file -> walletChargeLog.addWalletChargeFile(this.saveWalletChargeFile(request, walletChargeLog, file, "CHARGE")));
        Integer walletChargeLogId = this.walletChargeLogRepository.save(walletChargeLog).getId();

        // wallet_log 등록
        WalletDto.Request.SaveWalletLog saveWalletLog = new WalletDto.Request.SaveWalletLog();
        saveWalletLog.setBusinessAccountId(request.getBusinessAccountId());
        saveWalletLog.setSummary("charge");
        saveWalletLog.setInAmount(request.getDepositAmount());
        saveWalletLog.setOutAmount(0.0F);
        saveWalletLog.setMemo(request.getAdminMemo());
        saveWalletLog.setWalletChargeLogId(walletChargeLogId);
        saveWalletLog.setWalletRefundId(0);
        saveWalletLog.setWalletAutoChargeLogId(0);

        WalletLog walletLog = this.walletLogMapper.toEntity(saveWalletLog, loginUserNo);
        this.walletLogRepository.save(walletLog);

        // Master 금액 업데이트
        WalletDto.Response.WalletMaster list = this.walletMasterRepository.getWalletMaster(request.getBusinessAccountId());
        this.walletMasterRepository.updateWalletMasterCharge(request.getBusinessAccountId(), (float)(list.getAvailableAmount() + request.getDepositAmount()));




//        this.adAccountRepository.outOfBalanceUpdate(request.getAdAccountId(), false);

//        WalletDto.Response.WalletCashTotal list = walletCashTotalRepository.getCashTotalByCashId(request.getAdAccountId(), request.getCashId());
//        // 총합캐시
//        Long prevAmount = list.getAmount();
//        // 가용캐시
//        Long prevAvailableAmount = list.getAvailableAmount();
//
//        // total 캐시 변경
//        Long amount = prevAmount + request.getPubAmount();
//        Long availableAmount = prevAvailableAmount + request.getPubAmount();
//
//        this.adAccountRepository.outOfBalanceUpdate(request.getAdAccountId(), false);
//        WalletLog walletLog = this.walletLogMapper.toEntity(request, loginUserNo);
//        this.walletCashTotalRepository.updateWalletCashAdd(request.getAdAccountId(), request.getCashId(), amount, availableAmount);
//        this.walletLogRepository.save(walletLog);
    }

    @SneakyThrows
    private WalletChargeFile saveWalletChargeFile(WalletDto.Request.SaveCredit request, WalletChargeLog walletChargeLog, MultipartFile file, String fType) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
//        String savedFileUrl = this.fileService.saveWalletCharge(request, file);
        String savedFileUrl = this.awsFileService.saveWalletCharge(request, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new WalletChargeFile(walletChargeLog, this.walletChargeFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation walletChargeFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
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

    public void saveRefund(WalletDto.Request.SaveRefund request, Integer loginUserNo) {
        WalletRefund walletRefund = this.walletRefundMapper.toEntity(request, loginUserNo);
        this.walletRefundRepository.save(walletRefund);
    }

    public void updateRefundApprove(WalletDto.Request.UpdateRefund request, Integer loginUserNo) {
        WalletRefund walletRefund = WalletRefundFindUtils.findByIdOrElseThrow(request.getId(), this.walletRefundRepository);

        // 이미 승인했는지 체크
        if(walletRefund.getSendYN().equals(WalletRefund.SendYN.Y)) {
            throw new WalletRefundAlreadyException();
        }
        // 이미 거절했는지 체크
        if(walletRefund.getSendYN().equals(WalletRefund.SendYN.R)) {
            throw new WalletRefundAlreadyRejectException();
        }

        // wallet_log 등록
        WalletDto.Request.SaveWalletLog saveWalletLog = new WalletDto.Request.SaveWalletLog();
        saveWalletLog.setBusinessAccountId(request.getBusinessAccountId());
        saveWalletLog.setSummary("refund");
        saveWalletLog.setInAmount(request.getAmount());
        saveWalletLog.setOutAmount(0.0F);
        saveWalletLog.setMemo(request.getAdminMemo());
        saveWalletLog.setWalletChargeLogId(0);
        saveWalletLog.setWalletRefundId(request.getId());
        saveWalletLog.setWalletAutoChargeLogId(0);

        WalletLog walletLog = this.walletLogMapper.toEntity(saveWalletLog, loginUserNo);
        this.walletLogRepository.save(walletLog);

        // 계좌에서 돈 빼기
        WalletDto.Response.WalletMaster list = this.walletMasterRepository.getWalletMaster(request.getBusinessAccountId());
        this.walletMasterRepository.updateWalletMasterCharge(request.getBusinessAccountId(), (float)(list.getAvailableAmount() - request.getAmount()));

        // 환불 내역 업데이트
        walletRefund.updateApprove(request, loginUserNo);
        request.getWalletRefundFiles().forEach(file -> walletRefund.addWalletRefundFile(this.saveWalletRefundFile(request, walletRefund, file, "REFUND")));
    }

    public void updateRefundReject(WalletDto.Request.UpdateRefund request, Integer loginUserNo) {
        WalletRefund walletRefund = WalletRefundFindUtils.findByIdOrElseThrow(request.getId(), this.walletRefundRepository);

        // 이미 승인했는지 체크
        if(walletRefund.getSendYN().equals(WalletRefund.SendYN.Y)) {
            throw new WalletRefundAlreadyException();
        }
        // 이미 거절했는지 체크
        if(walletRefund.getSendYN().equals(WalletRefund.SendYN.R)) {
            throw new WalletRefundAlreadyRejectException();
        }

        // 환불거절
        walletRefund.updateReject(request, loginUserNo);
        request.getWalletRefundFiles().forEach(file -> walletRefund.addWalletRefundFile(this.saveWalletRefundFile(request, walletRefund, file, "REFUND")));
    }

    @SneakyThrows
    private WalletRefundFile saveWalletRefundFile(WalletDto.Request.UpdateRefund request, WalletRefund walletRefund, MultipartFile file, String fType) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
//        String savedFileUrl = this.fileService.saveWalletRefund(request, file);
        String savedFileUrl = this.awsFileService.saveWalletRefund(request, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new WalletRefundFile(walletRefund, this.walletRefundFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation walletRefundFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
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







    public void saveFreeCash(WalletDto.Request.SaveFreeCash request, Integer loginUserNo) {
//        WalletFreeCash walletFreeCash = this.walletFreeCashMapper.toEntity(request, loginUserNo);
//        this.walletFreeCashRepository.save(walletFreeCash);
    }

    public void updateFreeCashStatus(Integer id, String status, Integer loginUserNo) {
//        if(status.equals("USED")) {
//            WalletFreeCash walletFreeCash = walletFreeCashRepository.findById(id).orElseThrow(CashNotFoundException::new);
//
//            if (walletFreeCash.getStatus().toString().equals("READY")) {
//                WalletDto.Response.WalletCashTotal list = walletCashTotalRepository.getCashTotalByCashId(walletFreeCash.getAdAccountId(), walletFreeCash.getCashId());
//                // 총합캐시
//                Long prevAmount = list.getAmount();
//                // 가용캐시
//                Long prevAvailableAmount = list.getAvailableAmount();
//
//                // total 캐시 변경
//                Long amount = prevAmount + walletFreeCash.getPubAmount();
//                Long availableAmount = prevAvailableAmount + walletFreeCash.getPubAmount();
//
//                WalletDto.Request.SaveCash request = new WalletDto.Request.SaveCash();
//                request.setAdAccountId(walletFreeCash.getAdAccountId());
//                request.setCashId(walletFreeCash.getCashId());
//                request.setSummary("charge");
//                request.setInAmount(walletFreeCash.getPubAmount());
//                request.setOutAmount(0L);
//                request.setMemo("use free cash");
//
//                this.adAccountRepository.outOfBalanceUpdate(request.getAdAccountId(), false);
//                WalletLog walletLog = this.walletLogMapper.toEntity(request, loginUserNo);
//                this.walletCashTotalRepository.updateWalletCashAdd(request.getAdAccountId(), request.getCashId(), amount, availableAmount);
//                this.walletLogRepository.save(walletLog);
//
//                this.walletFreeCashRepository.updateFreeCashStats(id, status, loginUserNo);
//            }
//        } else {
//            this.walletFreeCashRepository.updateFreeCashStats(id, status, loginUserNo);
//        }
    }
}
