package com.adplatform.restApi.batch.service;

import com.adplatform.restApi.batch.dao.BatchStatusRepository;
import com.adplatform.restApi.batch.dao.mapper.BatchQueryMapper;
import com.adplatform.restApi.batch.dao.mapper.BatchSaveQueryMapper;
import com.adplatform.restApi.batch.domain.BatchStatus;
import com.adplatform.restApi.batch.dto.BatchStatusDto;
import com.adplatform.restApi.batch.dto.BatchStatusMapper;
import com.adplatform.restApi.domain.statistics.dao.sale.SaleAmountDailyRepository;
import com.adplatform.restApi.domain.statistics.dao.sale.SaleDetailAmountDailyRepository;
import com.adplatform.restApi.domain.statistics.dao.sale.SaleRemainAmountDailyRepository;
import com.adplatform.restApi.domain.statistics.domain.sale.SaleAmountDaily;
import com.adplatform.restApi.domain.statistics.domain.sale.SaleDetailAmountDaily;
import com.adplatform.restApi.domain.statistics.domain.sale.SaleRemainAmountDaily;
import com.adplatform.restApi.domain.statistics.dto.SaleAmountDto;
import com.adplatform.restApi.domain.statistics.dto.SaleAmountMapper;
import com.adplatform.restApi.domain.wallet.dao.walletcashtotal.WalletCashTotalRepository;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
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
public class BatchSaveService {

    private final BatchQueryMapper batchQueryMapper;
    private final BatchSaveQueryMapper batchSaveQueryMapper;
    private final BatchStatusMapper batchStatusMapper;
    private final BatchStatusRepository batchStatusRepository;
    private final SaleAmountMapper saleAmountMapper;
    private final WalletCashTotalRepository walletCashTotalRepository;
    private final SaleAmountDailyRepository saleAmountDailyRepository;
    private final SaleDetailAmountDailyRepository saleDetailAmountDailyRepository;
    private final SaleRemainAmountDailyRepository saleRemainAmountDailyRepository;



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
            exeDate = Integer.parseInt(CommonUtils.getBeforeYearMonthDayByYMD(String.valueOf(reportDate), -1));
        }

        this.saleAmountDaily(exeDate);
        this.adAccountSettlementDaily(exeDate);
        this.mediaSettlementDaily(exeDate);

        ////////////////////////////////////////////////////////////
        // Month Batch
        ////////////////////////////////////////////////////////////
        Calendar calendarMonth = new GregorianCalendar();
        SimpleDateFormat SDFMonth = new SimpleDateFormat("yyyyMMdd");
        String chkMonthDate = SDFMonth.format(calendarMonth.getTime());

        int exeMonthDate = 0;
        if (reportDate.equals(0)) {
            exeMonthDate = Integer.parseInt(chkMonthDate);
        } else {
            exeMonthDate = reportDate;
        }

        if(String.valueOf(exeMonthDate).substring(6, 8).equals("01")) {
            String BeforeMonthFirstDate = CommonUtils.getBeforeYearMonthByYMD(String.valueOf(exeMonthDate), -1);
            String BeforeMonthLastDate = CommonUtils.getLastDayOfMonthByYMD(BeforeMonthFirstDate);
            this.adAccountSettlementMonthly(Integer.parseInt(BeforeMonthFirstDate), Integer.parseInt(BeforeMonthLastDate), Integer.parseInt(BeforeMonthLastDate));
            this.adAccountTaxBillMonthly(Integer.parseInt(BeforeMonthFirstDate), Integer.parseInt(BeforeMonthLastDate), Integer.parseInt(BeforeMonthLastDate));
            // 매체 정산은 어떻게 할지 결정 후 추가
//            this.mediaSettlementMonthly(Integer.parseInt(BeforeMonthFirstDate), Integer.parseInt(BeforeMonthLastDate), Integer.parseInt(BeforeMonthLastDate));
            this.mediaTaxBillMonthly(Integer.parseInt(BeforeMonthFirstDate), Integer.parseInt(BeforeMonthLastDate), Integer.parseInt(BeforeMonthLastDate));
        }



    }

    public void saleAmountDaily(Integer exeDate) {

        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "D";
        String batchName = "sale_amount_daily";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, batchName);
        if (cnt > 0) {
            return;
        }

        ////////////////////////////////////////////////////////////
        // 일별 금액 계산
        ////////////////////////////////////////////////////////////
        List<BatchStatusDto.Response.ReportAdGroupCost> reportAdGroupCostsList = this.batchQueryMapper.reportAdGroupCost();

        for (BatchStatusDto.Response.ReportAdGroupCost co: reportAdGroupCostsList) {
            exeDate = co.getReportDate();

            List<WalletDto.Response.WalletCashTotal> walletCashTotalList = this.walletCashTotalRepository.getWalletCashTotal(co.getAdAccountId());

            Long amount = 0L;
            Long reserveAmount = 0L;
            Long useCost = 0L;
            Long totalUseCost = 0L;
            Long remainCost = co.getCost();
            Long remainCostTmp = remainCost;
            Boolean isLoop = true;
            for (WalletDto.Response.WalletCashTotal wa: walletCashTotalList) {
                if(wa.getReserveAmount() >= remainCost) {
                    amount = wa.getAmount() - remainCost;
                    reserveAmount = wa.getReserveAmount() - remainCost;
                    useCost = remainCost;
                    totalUseCost += remainCost;
                    remainCost = 0L;
                    isLoop = false;
                } else {
                    if(remainCost - wa.getReserveAmount() >= 0) {
                        remainCostTmp = wa.getReserveAmount();
                        amount = wa.getAmount() - wa.getReserveAmount();
                        reserveAmount = 0L;
                    }
                    useCost = wa.getReserveAmount();
                    totalUseCost += wa.getReserveAmount();
                    remainCost = remainCost - wa.getReserveAmount();
                    isLoop = true;
                }
                this.walletCashTotalRepository.saveWalletCashSettle(co.getAdAccountId(), wa.getCashId(), amount, reserveAmount);
//                System.out.println("===================================================");
//                System.out.println("detail");
//                System.out.println("===================================================");
//                System.out.println("co.getAdAccountId() : " + co.getAdAccountId());
//                System.out.println("wa.getCashId() : " + wa.getCashId());
//                System.out.println("amount : " + amount);
//                System.out.println("reserveAmount : " + reserveAmount);
//                System.out.println("remainCost : " + remainCost);
//                System.out.println("useCost : " + useCost);

                // detail
                if(useCost > 0) {
                    SaleAmountDto.Request.Save saleDetailList = new SaleAmountDto.Request.Save();
                    saleDetailList.setStatDate(co.getReportDate());
                    saleDetailList.setAdAccountId(co.getAdAccountId());
                    saleDetailList.setCashId(wa.getCashId());
                    saleDetailList.setCompanyId(co.getCompanyId());
                    saleDetailList.setOwnerCompanyId(co.getOwnerCompanyId());
                    saleDetailList.setSaleAmount(useCost.intValue());
                    SaleDetailAmountDaily saleDetailAmountDaily = this.saleAmountMapper.toEntityDetail(saleDetailList);
                    this.saleDetailAmountDailyRepository.save(saleDetailAmountDaily);
                }

                if(!isLoop) {
                    break;
                }
            }
//            System.out.println("===================================================");
//            System.out.println("Total");
//            System.out.println("===================================================");
//            System.out.println("co.getReportDate() : " + co.getReportDate());
//            System.out.println("co.getAdAccountId() : " + co.getAdAccountId());
//            System.out.println("totalUseCost : " + totalUseCost);

            // total
            SaleAmountDto.Request.Save saleList = new SaleAmountDto.Request.Save();
            saleList.setStatDate(co.getReportDate());
            saleList.setAdAccountId(co.getAdAccountId());
            saleList.setCompanyId(co.getCompanyId());
            saleList.setOwnerCompanyId(co.getOwnerCompanyId());
            saleList.setSaleAmount(totalUseCost.intValue());
            SaleAmountDaily saleAmountDaily = this.saleAmountMapper.toEntity(saleList);
            this.saleAmountDailyRepository.save(saleAmountDaily);

            // remain Cash
            SaleAmountDto.Request.Save saleRemainList = new SaleAmountDto.Request.Save();
            saleRemainList.setStatDate(co.getReportDate());
            saleRemainList.setAdAccountId(co.getAdAccountId());
            saleRemainList.setCompanyId(co.getCompanyId());
            saleRemainList.setOwnerCompanyId(co.getOwnerCompanyId());
            saleRemainList.setRemainAmount(co.getCost().intValue() - totalUseCost.intValue());
            SaleRemainAmountDaily saleRemainAmountDaily = this.saleAmountMapper.toEntityRemain(saleRemainList);
            this.saleRemainAmountDailyRepository.save(saleRemainAmountDaily);
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

    public void adAccountSettlementDaily(Integer exeDate) {

        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "D";
        String batchName = "adaccount_settlement_daily";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, "sale_amount_daily");
        if (repCnt == 0) {
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

        ////////////////////////////////////////////////////////////
        // 일별 정산
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertAdAccountSettlementDaily(exeDate);

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

    public void mediaSettlementDaily(Integer exeDate) {

        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "D";
        String batchName = "media_settlement_daily";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, batchName);
        if (cnt > 0) {
            return;
        }

        ////////////////////////////////////////////////////////////
        // 일별 정산
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertMediaSettlementDaily(exeDate);

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

    public void adAccountSettlementMonthly(Integer firstDate, Integer lastDate, Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "adaccount_settlement_monthly";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount("D", lastDate, "adaccount_settlement_daily");
        if (repCnt == 0) {
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

        ////////////////////////////////////////////////////////////
        // 일별 정산
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertAdAccountSettlementMonthly(firstDate, lastDate, exeDate);

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

    public void mediaSettlementMonthly(Integer firstDate, Integer lastDate, Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "media_settlement_monthly";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount("D", lastDate, "media_settlement_daily");
        if (repCnt == 0) {
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

        ////////////////////////////////////////////////////////////
        // 일별 정산
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertMediaSettlementMonthly(firstDate, lastDate, exeDate);

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

    public void adAccountTaxBillMonthly(Integer firstDate, Integer lastDate, Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "adaccount_tax_bill_monthly";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount("D", lastDate, "adaccount_settlement_daily");
        if (repCnt == 0) {
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

        ////////////////////////////////////////////////////////////
        // 일별 정산
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertAdAccountTaxBillMonthly(firstDate, lastDate, exeDate);

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

    public void mediaTaxBillMonthly(Integer firstDate, Integer lastDate, Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "media_tax_bill_monthly";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount("D", lastDate, "media_settlement_daily");
        if (repCnt == 0) {
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

        ////////////////////////////////////////////////////////////
        // 일별 정산
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertMediaTaxBillMonthly(firstDate, lastDate, exeDate);

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
