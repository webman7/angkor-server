package com.adplatform.restApi.batch.service;

import com.adplatform.restApi.batch.dao.BatchStatusRepository;
import com.adplatform.restApi.batch.dao.mapper.BatchQueryMapper;
import com.adplatform.restApi.batch.dao.mapper.BatchSaveQueryMapper;
import com.adplatform.restApi.batch.domain.BatchStatus;
import com.adplatform.restApi.batch.dto.BatchStatusDto;
import com.adplatform.restApi.batch.dto.BatchStatusMapper;
import com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve.WalletCampaignReserveDetailRepository;
import com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve.WalletCampaignReserveRepository;
import com.adplatform.restApi.domain.wallet.dao.walletlog.WalletLogRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterDetailRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletCampaignReserveDetail;
import com.adplatform.restApi.domain.wallet.domain.WalletLog;
import com.adplatform.restApi.domain.wallet.domain.WalletMasterDetail;
import com.adplatform.restApi.domain.wallet.dto.WalletCampaignReserveDetailMapper;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.domain.wallet.dto.WalletLogMapper;
import com.adplatform.restApi.domain.wallet.dto.WalletMasterDetailMapper;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BatchThirdSaveService {
    private final BatchQueryMapper batchQueryMapper;
    private final BatchSaveQueryMapper batchSaveQueryMapper;
    private final BatchStatusMapper batchStatusMapper;
    private final BatchStatusRepository batchStatusRepository;
    private final WalletCampaignReserveDetailMapper walletCampaignReserveDetailMapper;
    private final WalletCampaignReserveDetailRepository walletCampaignReserveDetailRepository;
    private final WalletCampaignReserveRepository walletCampaignReserveRepository;
    private final WalletMasterRepository walletMasterRepository;
    private final WalletMasterDetailMapper walletMasterDetailMapper;
    private final WalletMasterDetailRepository walletMasterDetailRepository;
    private final WalletLogMapper walletLogMapper;
    private final WalletLogRepository walletLogRepository;

    public void batchJob(Integer reportDate) {
        ////////////////////////////////////////////////////////////
        // Daily Batch
        ////////////////////////////////////////////////////////////
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String chkDate = SDF.format(calendar.getTime());

        int exeDate = 0;
        if (reportDate.equals(0)) {
            exeDate = Integer.parseInt(chkDate);
        } else {
            exeDate = Integer.parseInt(CommonUtils.getBeforeYearMonthDayByYMD(String.valueOf(reportDate), 1));
        }

        // 6일날 전월 데이터를 정산한다.(캠페인별)
//        if (String.valueOf(exeDate).endsWith("06")) {
        this.campaignFinishSettlement(exeDate);
//        }



//        this.saleAmountDaily(exeDate);
//        this.mediaSettlementDaily(exeDate);
    }

    public void campaignFinishSettlement(Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "campaign_finish_settlement";

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, "campaign_reserve_settlement");
        if (repCnt == 0) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return;
        }

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, batchName);
        if (cnt > 0) {
            return;
        }

        String beforeMonthFirstDate = CommonUtils.getBeforeYearMonthByYMD(String.valueOf(exeDate), 1);
        String beforeMonthLastDate = CommonUtils.getLastDayOfMonthByYMD(beforeMonthFirstDate);

        beforeMonthFirstDate = beforeMonthFirstDate.substring(0, 6) + "01";

        ////////////////////////////////////////////////////////////
        // 종료 캠페인 조회
        ////////////////////////////////////////////////////////////
        List<BatchStatusDto.Response.CampaignFinish> campaignFinishes = this.batchQueryMapper.campaignFinish(Integer.parseInt(beforeMonthFirstDate), Integer.parseInt(beforeMonthLastDate));

        for (BatchStatusDto.Response.CampaignFinish co: campaignFinishes) {

            // 현재 캠페인 예약금액
            WalletDto.Response.WalletCampaignReserve walletCampaignReserve = this.walletCampaignReserveRepository.getCampaignReserveAmount(co.getBusinessAccountId(), co.getAdAccountId(), co.getCampaignId());
            Float campaignReserveAmount = walletCampaignReserve.getReserveAmount();

            // 지갑캠페인예약 금액 변경
            this.walletCampaignReserveRepository.updateCampaignReserveAmount(co.getBusinessAccountId(), co.getAdAccountId(), co.getCampaignId(), 0.0F);

            // 지갑캠페인예약상세내역
            WalletDto.Request.SaveWalletCampaignReserveDetail detail = new WalletDto.Request.SaveWalletCampaignReserveDetail();
            detail.setBusinessAccountId(co.getBusinessAccountId());
            detail.setAdAccountId(co.getAdAccountId());
            detail.setCampaignId(co.getCampaignId());
            detail.setFluctuation("M");
            detail.setSummary("campaign_finish");
            detail.setChangeAmount(-campaignReserveAmount);
            detail.setReserveAmount(campaignReserveAmount);
            detail.setReserveChangeAmount(0.0F);
            WalletCampaignReserveDetail walletCampaignReserveDetail = this.walletCampaignReserveDetailMapper.toEntity(detail, SecurityUtils.getLoginUserNo());
            this.walletCampaignReserveDetailRepository.save(walletCampaignReserveDetail);

            WalletDto.Response.WalletMaster list = this.walletMasterRepository.getWalletMaster(co.getBusinessAccountId());

            // 가용금액 조회
            Float availableAmountTotal = 0.0F;
            availableAmountTotal += list.getAvailableAmount();

            // 얘약금액 조회
            Float totalReserveAmount = 0.0F;
            totalReserveAmount += list.getTotalReserveAmount();

            // 지갑 업데이트
            this.walletMasterRepository.updateWalletMaster(co.getBusinessAccountId(), availableAmountTotal + campaignReserveAmount, totalReserveAmount - campaignReserveAmount);

            // 지갑 상세 인서트
            WalletDto.Request.SaveWalletMasterDetail saveWalletMasterDetail = new WalletDto.Request.SaveWalletMasterDetail();
            saveWalletMasterDetail.setBusinessAccountId(co.getBusinessAccountId());
            saveWalletMasterDetail.setAvailableAmount(availableAmountTotal);
            saveWalletMasterDetail.setTotalReserveAmount(totalReserveAmount);
            saveWalletMasterDetail.setChangeAmount(campaignReserveAmount);
            saveWalletMasterDetail.setChangeReserveAmount(-campaignReserveAmount);
            saveWalletMasterDetail.setChangeAvailableAmount(availableAmountTotal + campaignReserveAmount);
            saveWalletMasterDetail.setChangeTotalReserveAmount(totalReserveAmount - campaignReserveAmount);
            saveWalletMasterDetail.setSummary("campaign_finish");
            saveWalletMasterDetail.setMemo(beforeMonthFirstDate.substring(0, 6) + "-" + co.getCampaignName() + "(" + co.getCampaignId() + ")");
            WalletMasterDetail walletMasterDetail = this.walletMasterDetailMapper.toEntity(saveWalletMasterDetail, SecurityUtils.getLoginUserNo());
            this.walletMasterDetailRepository.save(walletMasterDetail);

            // wallet_log 등록
            WalletDto.Request.SaveWalletLog saveWalletLog = new WalletDto.Request.SaveWalletLog();
            saveWalletLog.setBusinessAccountId(co.getBusinessAccountId());
            saveWalletLog.setSummary("campaign_finish");
            saveWalletLog.setChangeAmount(campaignReserveAmount);
            saveWalletLog.setAvailableAmount(availableAmountTotal);
            saveWalletLog.setChangeAvailableAmount(availableAmountTotal + campaignReserveAmount);
            saveWalletLog.setMemo(beforeMonthFirstDate.substring(0, 6) + "-" + co.getCampaignName() + "(" + co.getCampaignId() + ")");
            saveWalletLog.setWalletChargeLogId(0);
            saveWalletLog.setWalletRefundId(0);
            saveWalletLog.setWalletAutoChargeLogId(0);

            WalletLog walletLog = this.walletLogMapper.toEntity(saveWalletLog, SecurityUtils.getLoginUserNo());
            this.walletLogRepository.save(walletLog);










        }

        ////////////////////////////////////////////////////////////
        // 진행 완료
        ////////////////////////////////////////////////////////////
        // Batch Execution
        BatchStatusDto.Request.Save saveList = new BatchStatusDto.Request.Save();
        saveList.setType(batchType);
        saveList.setExeDate(exeDate);
        saveList.setName(batchName);
        saveList.setExeYn(true);
        BatchStatus batchStatus = this.batchStatusMapper.toEntity(saveList);
        this.batchStatusRepository.save(batchStatus);

    }
}
