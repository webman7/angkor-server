package com.adplatform.restApi.batch.service;

import com.adplatform.restApi.batch.dao.BatchStatusRepository;
import com.adplatform.restApi.batch.dao.mapper.BatchQueryMapper;
import com.adplatform.restApi.batch.dao.mapper.BatchSaveQueryMapper;
import com.adplatform.restApi.batch.domain.BatchStatus;
import com.adplatform.restApi.batch.dto.BatchStatusDto;
import com.adplatform.restApi.batch.dto.BatchStatusMapper;
import com.adplatform.restApi.domain.history.dao.campaign.CampaignBudgetChangeHistoryRepository;
import com.adplatform.restApi.domain.history.domain.CampaignBudgetChangeHistory;
import com.adplatform.restApi.domain.history.dto.campaign.CampaignBudgetChangeHistoryDto;
import com.adplatform.restApi.domain.history.dto.campaign.CampaignBudgetChangeHistoryMapper;
import com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve.WalletCampaignReserveDetailRepository;
import com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve.WalletCampaignReserveRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterDetailRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletCampaignReserveDetail;
import com.adplatform.restApi.domain.wallet.domain.WalletMasterDetail;
import com.adplatform.restApi.domain.wallet.dto.WalletCampaignReserveDetailMapper;
import com.adplatform.restApi.domain.wallet.dto.WalletCampaignReserveMapper;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
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
public class BatchSecondSaveService {
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

        // 6일날 전월 데이터로 세금계산서를 만든다.
        if (String.valueOf(exeDate).endsWith("06")) {
            this.businessAccountTaxBillMonthly(exeDate);
            // 캠페인 예약금액을 정산한다.
            this.campaignSettlementMonthly(exeDate);
        }

    }


    public void businessAccountTaxBillMonthly(Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "business_account_tax_bill";

        String beforeMonthFirstDate = CommonUtils.getBeforeYearMonthByYMD(String.valueOf(exeDate), 1);
        String beforeMonthLastDate = CommonUtils.getLastDayOfMonthByYMD(beforeMonthFirstDate);

        beforeMonthFirstDate = beforeMonthFirstDate.substring(0, 6) + "01";

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, Integer.parseInt(beforeMonthFirstDate), "campaign_settlement_daily");
        if (repCnt == 0) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return;
        }

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, Integer.parseInt(beforeMonthFirstDate), batchName);
        if (cnt > 0) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            return;
        }

        ////////////////////////////////////////////////////////////
        // 월별 세금계산서
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertBusinessAccountTaxBillMonthly(Integer.parseInt(beforeMonthFirstDate), Integer.parseInt(beforeMonthLastDate));

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

    public void campaignSettlementMonthly(Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "campaign_reserve_settlement";

        String beforeMonthFirstDate = CommonUtils.getBeforeYearMonthByYMD(String.valueOf(exeDate), 1);
        String beforeMonthLastDate = CommonUtils.getLastDayOfMonthByYMD(beforeMonthFirstDate);

        beforeMonthFirstDate = beforeMonthFirstDate.substring(0, 6) + "01";


        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, Integer.parseInt(beforeMonthFirstDate), "campaign_settlement_daily");
        if (repCnt == 0) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return;
        }

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, Integer.parseInt(beforeMonthFirstDate), batchName);
        if (cnt > 0) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            return;
        }

        ////////////////////////////////////////////////////////////
        // 캠페인 월 정산 금액 계산
        ////////////////////////////////////////////////////////////
        List<BatchStatusDto.Response.CampaignSettlementDaily> campaignSettlementDaily = this.batchQueryMapper.campaignSettlementDaily(Integer.parseInt(beforeMonthFirstDate), Integer.parseInt(beforeMonthLastDate));

        for (BatchStatusDto.Response.CampaignSettlementDaily co: campaignSettlementDaily) {

            // 현재 캠페인 예약금액
            WalletDto.Response.WalletCampaignReserve walletCampaignReserve = this.walletCampaignReserveRepository.getCampaignReserveAmount(co.getBusinessAccountId(), co.getAdAccountId(), co.getCampaignId());
            Float campaignReserveAmount = walletCampaignReserve.getReserveAmount();

            // 사용금액
            Float budgetAmount = -co.getSupplyAmount();

            // 캠페인 변경 금액
            Float reserveChangeAmount = campaignReserveAmount + budgetAmount;

            // 지갑캠페인예약 금액 변경
            this.walletCampaignReserveRepository.updateCampaignReserveAmount(co.getBusinessAccountId(), co.getAdAccountId(), co.getCampaignId(), reserveChangeAmount);

            // 지갑캠페인예약상세내역
            WalletDto.Request.SaveWalletCampaignReserveDetail detail = new WalletDto.Request.SaveWalletCampaignReserveDetail();
            detail.setBusinessAccountId(co.getBusinessAccountId());
            detail.setAdAccountId(co.getAdAccountId());
            detail.setCampaignId(co.getCampaignId());
            detail.setFluctuation("M");
            detail.setSummary("settlement");
            detail.setChangeAmount(budgetAmount);
            detail.setReserveAmount(campaignReserveAmount);
            detail.setReserveChangeAmount(reserveChangeAmount);
            WalletCampaignReserveDetail walletCampaignReserveDetail = this.walletCampaignReserveDetailMapper.toEntity(detail, SecurityUtils.getLoginUserNo());
            this.walletCampaignReserveDetailRepository.save(walletCampaignReserveDetail);
        }

        ////////////////////////////////////////////////////////////
        // 지갑 월 정산 금액 계산
        ////////////////////////////////////////////////////////////
        List<BatchStatusDto.Response.BusinessAccountSettlement> businessAccountSettlement = this.batchQueryMapper.businessAccountSettlement(Integer.parseInt(beforeMonthFirstDate), Integer.parseInt(beforeMonthLastDate));

        for (BatchStatusDto.Response.BusinessAccountSettlement co: businessAccountSettlement) {
            WalletDto.Response.WalletMaster list = this.walletMasterRepository.getWalletMaster(co.getBusinessAccountId());

            // 가용금액 조회
            Float availableAmountTotal = 0.0F;
            availableAmountTotal += list.getAvailableAmount();

            // 얘약금액 조회
            Float totalReserveAmount = 0.0F;
            totalReserveAmount += list.getTotalReserveAmount();

            // 지갑 업데이트
            this.walletMasterRepository.updateWalletMasterReserveAmount(co.getBusinessAccountId(), totalReserveAmount - co.getSupplyAmount());

            // 지갑 상세 인서트
            WalletDto.Request.SaveWalletMasterDetail saveWalletMasterDetail = new WalletDto.Request.SaveWalletMasterDetail();
            saveWalletMasterDetail.setBusinessAccountId(co.getBusinessAccountId());
            saveWalletMasterDetail.setAvailableAmount(availableAmountTotal);
            saveWalletMasterDetail.setTotalReserveAmount(totalReserveAmount);
            saveWalletMasterDetail.setChangeAmount(0.0F);
            saveWalletMasterDetail.setChangeReserveAmount(-co.getSupplyAmount());
            saveWalletMasterDetail.setChangeAvailableAmount(availableAmountTotal);
            saveWalletMasterDetail.setChangeTotalReserveAmount(totalReserveAmount - co.getSupplyAmount());
            saveWalletMasterDetail.setSummary("settlement");
            saveWalletMasterDetail.setMemo(beforeMonthFirstDate.substring(0, 6));
            WalletMasterDetail walletMasterDetail = this.walletMasterDetailMapper.toEntity(saveWalletMasterDetail, SecurityUtils.getLoginUserNo());
            this.walletMasterDetailRepository.save(walletMasterDetail);
        }

        ////////////////////////////////////////////////////////////
        // 진행 완료
        ////////////////////////////////////////////////////////////
        // Batch Execution
        BatchStatusDto.Request.Save saveList = new BatchStatusDto.Request.Save();
        saveList.setType(batchType);
        saveList.setExeDate(Integer.parseInt(beforeMonthFirstDate));
        saveList.setName(batchName);
        saveList.setExeYn(true);
        BatchStatus batchStatus = this.batchStatusMapper.toEntity(saveList);
        this.batchStatusRepository.save(batchStatus);

    }
}
