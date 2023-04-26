package com.adplatform.restApi.domain.advertiser.campaign.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.service.AdAccountFindUtils;
import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupEventMapper;
import com.adplatform.restApi.domain.advertiser.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.advertiser.campaign.dao.typegoal.AdTypeAndGoalRepository;
import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.advertiser.campaign.domain.AdTypeAndGoal;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignMapper;
import com.adplatform.restApi.domain.advertiser.campaign.exception.AdTypeAndGoalNotFoundException;
import com.adplatform.restApi.domain.advertiser.campaign.exception.CampaignCashException;
import com.adplatform.restApi.domain.business.dao.account.BusinessAccountRepository;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.service.BusinessAccountFindUtils;
import com.adplatform.restApi.domain.history.dao.AdminStopHistoryRepository;
import com.adplatform.restApi.domain.history.dao.campaign.CampaignBudgetChangeHistoryRepository;
import com.adplatform.restApi.domain.history.domain.AdminStopHistory;
import com.adplatform.restApi.domain.history.domain.CampaignBudgetChangeHistory;
import com.adplatform.restApi.domain.history.dto.AdminStopHistoryDto;
import com.adplatform.restApi.domain.history.dto.AdminStopHistoryMapper;
import com.adplatform.restApi.domain.history.dto.campaign.CampaignBudgetChangeHistoryDto;
import com.adplatform.restApi.domain.history.dto.campaign.CampaignBudgetChangeHistoryMapper;
import com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve.WalletCampaignReserveDetailRepository;
import com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve.WalletCampaignReserveRepository;
import com.adplatform.restApi.domain.wallet.dao.walletlog.WalletLogRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterDetailRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletReserveLogRepository;
import com.adplatform.restApi.domain.wallet.domain.*;
import com.adplatform.restApi.domain.wallet.dto.*;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CampaignCommandService {
    private final ApplicationEventPublisher eventPublisher;
    private final CampaignRepository campaignRepository;
    private final AdTypeAndGoalRepository adTypeAndGoalRepository;
    private final AdAccountRepository adAccountRepository;
    private final CampaignMapper campaignMapper;
    private final AdGroupEventMapper adGroupEventMapper;
    private final CampaignBudgetChangeHistoryRepository campaignBudgetChangeHistoryRepository;
    private final CampaignBudgetChangeHistoryMapper campaignBudgetChangeHistoryMapper;
    private final BusinessAccountRepository businessAccountRepository;
    private final WalletMasterRepository walletMasterRepository;
    private final AdGroupRepository adGroupRepository;
    private final AdminStopHistoryMapper adminStopHistoryMapper;
    private final AdminStopHistoryRepository adminStopHistoryRepository;
    private final WalletCampaignReserveDetailMapper walletCampaignReserveDetailMapper;
    private final WalletCampaignReserveDetailRepository walletCampaignReserveDetailRepository;
    private final WalletCampaignReserveMapper walletCampaignReserveMapper;
    private final WalletCampaignReserveRepository walletCampaignReserveRepository;
    private final WalletMasterDetailMapper walletMasterDetailMapper;
    private final WalletMasterDetailRepository walletMasterDetailRepository;
    private final WalletLogMapper walletLogMapper;
    private final WalletLogRepository walletLogRepository;


    public void save(CampaignDto.Request.Save request) {
        CampaignDto.Response.CampaignByBusinessAccountId campaignInfo = this.campaignRepository.getCampaignByBusinessAccountId(request.getAdAccountId());

        AdTypeAndGoal adTypeAndGoal = this.findAdTypeAndGoal(
                request.getAdTypeAndGoal().getAdTypeName(),
                request.getAdTypeAndGoal().getAdGoalName());
        BusinessAccount businessAccount = BusinessAccountFindUtils.findByIdOrElseThrow(campaignInfo.getBusinessAccountId(), this.businessAccountRepository);
        AdAccount adAccount = AdAccountFindUtils.findByIdOrElseThrow(request.getAdAccountId(), this.adAccountRepository);

        Campaign campaign = this.campaignRepository.save(this.campaignMapper.toEntity(request, adTypeAndGoal, adAccount));
        this.mapToAdGroupSavedEvent(request.getAdGroups(), campaign).forEach(this.eventPublisher::publishEvent);

        // 선불 결제 방식
        if(businessAccount.isPrePayment()) {
            WalletDto.Response.WalletMaster list = this.walletMasterRepository.getWalletMaster(campaignInfo.getBusinessAccountId());

            // 이용가능금액 조회
            Float availableAmountTotal = 0.0F;
            availableAmountTotal += list.getAvailableAmount();

            // 현재 캠페인 예약금액
            Float campaignReserveAmount = 0.0F;

            // 가용금액, 총예약금액
            Float availableAmount = 0.0F;
            Float totalReserveAmount = 0.0F;

            // 예산을 예약금액으로 잡는다.
            Float budgetAmount = (float)(request.getBudgetAmount());

            // 캠페인 변경 금액
            Float reserveChangeAmount = campaignReserveAmount + budgetAmount;

            // 예약금액 증가
            String fluctuation = "P";
            if(availableAmountTotal > budgetAmount) {
                availableAmount = list.getAvailableAmount() - budgetAmount;
                totalReserveAmount = list.getTotalReserveAmount() + budgetAmount;

                // 지갑 업데이트
                this.walletMasterRepository.updateWalletMaster(campaignInfo.getBusinessAccountId(), availableAmount, totalReserveAmount);

                // 지갑 상세 인서트
                WalletDto.Request.SaveWalletMasterDetail saveWalletMasterDetail = new WalletDto.Request.SaveWalletMasterDetail();
                saveWalletMasterDetail.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                saveWalletMasterDetail.setAvailableAmount(list.getAvailableAmount());
                saveWalletMasterDetail.setTotalReserveAmount(list.getTotalReserveAmount());
                saveWalletMasterDetail.setChangeAmount(budgetAmount);
                saveWalletMasterDetail.setChangeReserveAmount(-budgetAmount);
                saveWalletMasterDetail.setChangeAvailableAmount(availableAmount);
                saveWalletMasterDetail.setChangeTotalReserveAmount(totalReserveAmount);
                saveWalletMasterDetail.setSummary("campaign_budget");
                saveWalletMasterDetail.setMemo("campaignId : " + campaign.getId());
                WalletMasterDetail walletMasterDetail = this.walletMasterDetailMapper.toEntity(saveWalletMasterDetail, SecurityUtils.getLoginUserNo());
                this.walletMasterDetailRepository.save(walletMasterDetail);

                // wallet_log 등록
                WalletDto.Request.SaveWalletLog saveWalletLog = new WalletDto.Request.SaveWalletLog();
                saveWalletLog.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                saveWalletLog.setSummary("campaign_budget");
                saveWalletLog.setChangeAmount(budgetAmount);
                saveWalletLog.setAvailableAmount(availableAmountTotal);
                saveWalletLog.setChangeAvailableAmount(availableAmount);
                saveWalletLog.setMemo("campaignId : " + campaign.getId());
                saveWalletLog.setWalletChargeLogId(0);
                saveWalletLog.setWalletRefundId(0);
                saveWalletLog.setWalletAutoChargeLogId(0);

                WalletLog walletLog = this.walletLogMapper.toEntity(saveWalletLog, SecurityUtils.getLoginUserNo());
                this.walletLogRepository.save(walletLog);

                // 지갑캠페인예약 금액 등록
                WalletDto.Request.SaveWalletCampaignReserve saveWalletCampaignReserve = new WalletDto.Request.SaveWalletCampaignReserve();
                saveWalletCampaignReserve.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                saveWalletCampaignReserve.setAdAccountId(request.getAdAccountId());
                saveWalletCampaignReserve.setCampaignId(campaign.getId());
                saveWalletCampaignReserve.setReserveAmount(reserveChangeAmount);
                saveWalletCampaignReserve.setUpdateAt(LocalDateTime.now());
                WalletCampaignReserve walletCampaignReserve = this.walletCampaignReserveMapper.toEntity(saveWalletCampaignReserve, SecurityUtils.getLoginUserNo());
                this.walletCampaignReserveRepository.save(walletCampaignReserve);

                // 지갑캠페인예약상세내역
                WalletDto.Request.SaveWalletCampaignReserveDetail detail = new WalletDto.Request.SaveWalletCampaignReserveDetail();
                detail.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                detail.setAdAccountId(request.getAdAccountId());
                detail.setCampaignId(campaign.getId());
                detail.setFluctuation(fluctuation);
                detail.setSummary("campaign_budget");
                detail.setChangeAmount(budgetAmount);
                detail.setReserveAmount(campaignReserveAmount);
                detail.setReserveChangeAmount(reserveChangeAmount);
                WalletCampaignReserveDetail walletCampaignReserveDetail = this.walletCampaignReserveDetailMapper.toEntity(detail, SecurityUtils.getLoginUserNo());
                this.walletCampaignReserveDetailRepository.save(walletCampaignReserveDetail);

                CampaignBudgetChangeHistoryDto.Request.Save history = new CampaignBudgetChangeHistoryDto.Request.Save();
                history.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                history.setAdAccountId(request.getAdAccountId());
                history.setCampaignId(campaign.getId());
                history.setChgAmount(budgetAmount);
                history.setAvailableAmount(list.getAvailableAmount());
                history.setAvailableChgAmount(availableAmount);
                history.setTotalReserveAmount(list.getTotalReserveAmount());
                history.setTotalReserveChgAmount(totalReserveAmount);
                CampaignBudgetChangeHistory campaignBudgetChangeHistory = this.campaignBudgetChangeHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
                this.campaignBudgetChangeHistoryRepository.save(campaignBudgetChangeHistory);

            } else {
                throw new CampaignCashException();
            }
        }
    }

    private AdTypeAndGoal findAdTypeAndGoal(String adTypeName, String adGoalName) {
        return this.adTypeAndGoalRepository.findByAdType_NameAndAdGoal_Name(adTypeName, adGoalName)
                .orElseThrow(AdTypeAndGoalNotFoundException::new);
    }

    public void adGroupSave(AdGroupDto.Request.Save request) {
        Campaign campaign = CampaignFindUtils.findByIdOrElseThrow(request.getCampaignId(), this.campaignRepository);
        this.mapToAdGroupSavedEvent(request.getAdGroups(), campaign).forEach(this.eventPublisher::publishEvent);
    }

    private List<AdGroupSavedEvent> mapToAdGroupSavedEvent(List<AdGroupDto.Request.FirstSave> request, Campaign campaign) {
        return request.stream().map(a -> this.adGroupEventMapper.toEvent(a, campaign)).collect(Collectors.toList());
    }

    public void update(CampaignDto.Request.Update request) {

        CampaignDto.Response.CampaignByAdAccountId campaignInfo = this.campaignRepository.getCampaignByAdAccountId(request.getCampaignId());
        BusinessAccount businessAccount = BusinessAccountFindUtils.findByIdOrElseThrow(campaignInfo.getBusinessAccountId(), this.businessAccountRepository);

        // 광고그룹예산의 합보다 작게는 예산 감소를 할 수 없다.
        Long budgetSum = this.adGroupRepository.getBudgetSum(request.getCampaignId());
        if(budgetSum > request.getBudgetAmount()) {
            throw new CampaignCashException();
        }

        // 기본 정보 업데이트
        CampaignFindUtils.findByIdOrElseThrow(request.getCampaignId(), this.campaignRepository)
                .update(request);

        // 선불일 경우
        if(businessAccount.isPrePayment()) {
            // 캠페인 예산과 요청된 예산이 다를 경우만 진행한다.
            if(!request.getBudgetAmount().equals(campaignInfo.getBudgetAmount())) {
                WalletDto.Response.WalletMaster list = this.walletMasterRepository.getWalletMaster(campaignInfo.getBusinessAccountId());

                // 이용가능금액 조회
                Float availableAmountTotal = 0.0F;
                availableAmountTotal += list.getAvailableAmount();

                // 현재 캠페인 예약금액
                WalletDto.Response.WalletCampaignReserve walletCampaignReserve = this.walletCampaignReserveRepository.getCampaignReserveAmount(campaignInfo.getBusinessAccountId(), campaignInfo.getAdAccountId(), request.getCampaignId());
                Float campaignReserveAmount = walletCampaignReserve.getReserveAmount();

                // 가용금액, 총예약금액
                Float availableAmount = 0.0F;
                Float totalReserveAmount = 0.0F;

                Float budgetAmount = (float) request.getBudgetAmount() - (float) campaignInfo.getBudgetAmount();

                // 캠페인 변경 금액
                Float reserveChangeAmount = campaignReserveAmount + budgetAmount;

                // 예약금액 증감
                String fluctuation = "";
                if (budgetAmount > 0) {
                    fluctuation = "P";
                } else {
                    fluctuation = "M";
                }

                if (availableAmountTotal > budgetAmount) {
                    availableAmount = list.getAvailableAmount() - budgetAmount;
                    totalReserveAmount = list.getTotalReserveAmount() + budgetAmount;

                    // 예산 업데이트
                    CampaignFindUtils.findByIdOrElseThrow(request.getCampaignId(), this.campaignRepository)
                            .updateBudget(request);

                    // 지갑 업데이트
                    this.walletMasterRepository.updateWalletMaster(campaignInfo.getBusinessAccountId(), availableAmount, totalReserveAmount);

                    // 지갑 상세 인서트
                    WalletDto.Request.SaveWalletMasterDetail saveWalletMasterDetail = new WalletDto.Request.SaveWalletMasterDetail();
                    saveWalletMasterDetail.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                    saveWalletMasterDetail.setAvailableAmount(list.getAvailableAmount());
                    saveWalletMasterDetail.setTotalReserveAmount(list.getTotalReserveAmount());
                    saveWalletMasterDetail.setChangeAmount(-budgetAmount);
                    saveWalletMasterDetail.setChangeReserveAmount(budgetAmount);
                    saveWalletMasterDetail.setChangeAvailableAmount(availableAmount);
                    saveWalletMasterDetail.setChangeTotalReserveAmount(totalReserveAmount);
                    saveWalletMasterDetail.setSummary("campaign_budget");
                    saveWalletMasterDetail.setMemo("");
                    WalletMasterDetail walletMasterDetail = this.walletMasterDetailMapper.toEntity(saveWalletMasterDetail, SecurityUtils.getLoginUserNo());
                    this.walletMasterDetailRepository.save(walletMasterDetail);

                    // wallet_log 등록
                    WalletDto.Request.SaveWalletLog saveWalletLog = new WalletDto.Request.SaveWalletLog();
                    saveWalletLog.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                    saveWalletLog.setSummary("campaign_budget");
                    saveWalletLog.setChangeAmount(-budgetAmount);
                    saveWalletLog.setAvailableAmount(availableAmountTotal);
                    saveWalletLog.setChangeAvailableAmount(availableAmount);
                    saveWalletLog.setMemo("");
                    saveWalletLog.setWalletChargeLogId(0);
                    saveWalletLog.setWalletRefundId(0);
                    saveWalletLog.setWalletAutoChargeLogId(0);

                    WalletLog walletLog = this.walletLogMapper.toEntity(saveWalletLog, SecurityUtils.getLoginUserNo());
                    this.walletLogRepository.save(walletLog);

                    // 지갑캠페인예약 금액 변경
                    this.walletCampaignReserveRepository.updateCampaignReserveAmount(campaignInfo.getBusinessAccountId(), campaignInfo.getAdAccountId(), request.getCampaignId(), reserveChangeAmount);

                    // 지갑캠페인예약상세내역
                    WalletDto.Request.SaveWalletCampaignReserveDetail detail = new WalletDto.Request.SaveWalletCampaignReserveDetail();
                    detail.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                    detail.setAdAccountId(campaignInfo.getAdAccountId());
                    detail.setCampaignId(request.getCampaignId());
                    detail.setFluctuation(fluctuation);
                    detail.setSummary("campaign_budget");
                    detail.setChangeAmount(budgetAmount);
                    detail.setReserveAmount(campaignReserveAmount);
                    detail.setReserveChangeAmount(reserveChangeAmount);
                    WalletCampaignReserveDetail walletCampaignReserveDetail = this.walletCampaignReserveDetailMapper.toEntity(detail, SecurityUtils.getLoginUserNo());
                    this.walletCampaignReserveDetailRepository.save(walletCampaignReserveDetail);

                    // 캠페인 예산 변경 히스토리
                    CampaignBudgetChangeHistoryDto.Request.Save history = new CampaignBudgetChangeHistoryDto.Request.Save();
                    history.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                    history.setAdAccountId(campaignInfo.getAdAccountId());
                    history.setCampaignId(request.getCampaignId());
                    history.setChgAmount(budgetAmount);
                    history.setAvailableAmount(list.getAvailableAmount());
                    history.setAvailableChgAmount(availableAmount);
                    history.setTotalReserveAmount(list.getTotalReserveAmount());
                    history.setTotalReserveChgAmount(totalReserveAmount);
                    CampaignBudgetChangeHistory campaignBudgetChangeHistory = this.campaignBudgetChangeHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
                    this.campaignBudgetChangeHistoryRepository.save(campaignBudgetChangeHistory);

                } else {
                    throw new CampaignCashException();
                }
            }
        } else {
            // 예산 업데이트
            CampaignFindUtils.findByIdOrElseThrow(request.getCampaignId(), this.campaignRepository)
                    .updateBudget(request);
        }
    }

    public void delete(Integer id) {
        CampaignFindUtils.findByIdOrElseThrow(id, this.campaignRepository).delete();
    }

    public void changeConfig(Integer id, Campaign.Config config) {
        Campaign campaign = CampaignFindUtils.findByIdOrElseThrow(id, this.campaignRepository);
        if (config == Campaign.Config.ON) {
            // 기간 체크하여 Status 변경
            if(campaign.getSystemConfig().equals(Campaign.SystemConfig.ON)) {
                campaign.changeConfigOn();

                Calendar calendar = new GregorianCalendar();
                SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

                Integer chkDate = Integer.parseInt(SDF.format(calendar.getTime()));

                if (campaign.getStartDate() > chkDate) {
                    campaign.changeStatusReady();
                }
                else if( campaign.getStartDate() <= chkDate && campaign.getEndDate() >= chkDate) {
                    campaign.changeStatusLive();
                } else {
                    campaign.changeStatusFinished();
                }
            }
        } else if (config == Campaign.Config.OFF) {
            campaign.changeConfigOff();
            campaign.changeStatusOff();
        }
    }

    public void changeAdminStop(Integer id, CampaignDto.Request.AdminStop request, Boolean adminStop) {
        Campaign campaign = CampaignFindUtils.findByIdOrElseThrow(id, this.campaignRepository);
        if (adminStop) {
            // 히스토리 저장
            AdminStopHistoryDto.Request.Save history = new AdminStopHistoryDto.Request.Save();
            history.setType(request.getType());
            history.setStopId(id);
            history.setReason(request.getReason());
            AdminStopHistory adminStopHistory = this.adminStopHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
            this.adminStopHistoryRepository.save(adminStopHistory);

            // 관리자 정지
            campaign.changeAdminStopOn();
        }
        else {
            campaign.changeAdminStopOff();
        }
    }
}
