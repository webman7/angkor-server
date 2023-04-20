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
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletReserveLogRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletReserveLog;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.domain.wallet.dto.WalletReserveLogMapper;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
    private final WalletReserveLogRepository walletReserveLogRepository;
    private final WalletReserveLogMapper walletReserveLogMapper;
    private final AdGroupRepository adGroupRepository;
    private final AdminStopHistoryMapper adminStopHistoryMapper;
    private final AdminStopHistoryRepository adminStopHistoryRepository;

    public void save(CampaignDto.Request.Save request) {
        CampaignDto.Response.CampaignByBusinessAccountId campaignInfo = this.campaignRepository.getCampaignByBusinessAccountId(request.getAdAccountId());

        System.out.println("11111111111111111111");
        AdTypeAndGoal adTypeAndGoal = this.findAdTypeAndGoal(
                request.getAdTypeAndGoal().getAdTypeName(),
                request.getAdTypeAndGoal().getAdGoalName());
        BusinessAccount businessAccount = BusinessAccountFindUtils.findByIdOrElseThrow(campaignInfo.getBusinessAccountId(), this.businessAccountRepository);
        AdAccount adAccount = AdAccountFindUtils.findByIdOrElseThrow(request.getAdAccountId(), this.adAccountRepository);

        System.out.println("222222222222222222222222");
        Campaign campaign = this.campaignRepository.save(this.campaignMapper.toEntity(request, adTypeAndGoal, adAccount));
        System.out.println("33333333333333333333333");
        this.mapToAdGroupSavedEvent(request.getAdGroups(), campaign).forEach(this.eventPublisher::publishEvent);

        System.out.println("44444444444444444444444444");
        // 선불 결제 방식
        if(businessAccount.isPrePayment()) {
            WalletDto.Response.WalletMaster list = this.walletMasterRepository.getWalletMaster(campaignInfo.getBusinessAccountId());

            // 이용가능금액 조회
            Float availableAmountTotal = 0.0F;
            availableAmountTotal += list.getAvailableAmount();

            // 예산
            Float availableAmount = 0.0F;
            Float totalReserveAmount = 0.0F;

            // 예산에 부가세를 더한금액을 예약금액으로 잡는다.
            Float budgetAmount =  (float)(request.getBudgetAmount());
            Float budgetAmountVat =  (float)(budgetAmount * 1.1);
            // 예약금액 증가
            String fluctuation = "P";
            if(availableAmountTotal > budgetAmountVat) {
                availableAmount = list.getAvailableAmount() - budgetAmountVat;
                totalReserveAmount = list.getTotalReserveAmount() + budgetAmountVat;

                // 지갑 업데이트
                this.walletMasterRepository.updateWalletMaster(campaignInfo.getBusinessAccountId(), availableAmount, totalReserveAmount);

                // 지갑예약(예산)변경내역
                WalletDto.Request.SaveWalletReserveLog log = new WalletDto.Request.SaveWalletReserveLog();
                log.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                log.setAdAccountId(request.getAdAccountId());
                log.setCampaignId(campaign.getId());
                log.setFluctuation(fluctuation);
                log.setTotalReserveAmount(budgetAmountVat);
                log.setReserveAmount(budgetAmount);
                log.setReserveVatAmount((float)(budgetAmount*0.1));
                WalletReserveLog walletReserveLog = this.walletReserveLogMapper.toEntity(log, SecurityUtils.getLoginUserNo());
                this.walletReserveLogRepository.save(walletReserveLog);

                CampaignBudgetChangeHistoryDto.Request.Save history = new CampaignBudgetChangeHistoryDto.Request.Save();
                history.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                history.setAdAccountId(request.getAdAccountId());
                history.setCampaignId(campaign.getId());
                history.setChgAmount(budgetAmountVat);
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
        
        




//        List<WalletDto.Response.WalletCashTotal> list = this.walletCashTotalRepository.getWalletCashTotal(request.getAdAccountId());
//
//        // 이용가능금액 조회
//        Float availableAmountSum = 0.0F;
//        for (WalletDto.Response.WalletCashTotal m: list) {
//            availableAmountSum += m.getAvailableAmount();
//        }
//
//        // 이용가능 금액 보다 크다면
//        Float availableAmount = 0.0F;
//        Float reserveAmount = 0.0F;
//        Float budgetAmount =  (float)request.getBudgetAmount();
//        Float budgetAmountRemain = budgetAmount;
//        Boolean isLoop = true;
//        if(availableAmountSum > budgetAmount) {
//            for (WalletDto.Response.WalletCashTotal m: list) {
//                // 하나의 캐시가 큰 경우
//                if(m.getAvailableAmount() - budgetAmount > 0) {
//                    availableAmount = m.getAvailableAmount() - budgetAmount;
//                    reserveAmount = m.getReserveAmount() + budgetAmount;
//                    isLoop = false;
//                } else {
//                    budgetAmountRemain = budgetAmount - m.getAvailableAmount();
//                    if(budgetAmountRemain >= 0) {
//                        availableAmount = 0.0F;
//                        reserveAmount = m.getReserveAmount() + m.getAvailableAmount();
//                    }
//                    budgetAmount = budgetAmountRemain;
//                    isLoop = true;
//                }
//                this.walletCashTotalRepository.saveWalletCashReserve(request.getAdAccountId(), m.getCashId(), availableAmount, reserveAmount);
//
//                CampaignBudgetChangeHistoryDto.Request.Save history = new CampaignBudgetChangeHistoryDto.Request.Save();
//                history.setAdAccountId(request.getAdAccountId());
//                history.setCampaignId(campaign.getId());
//                history.setCashId(m.getCashId());
//                history.setChgAmount(budgetAmount);
//                history.setAvailableAmount(m.getAvailableAmount());
//                history.setAvailableChgAmount(availableAmount);
//                history.setReserveAmount(m.getReserveAmount());
//                history.setReserveChgAmount(reserveAmount);
//                CampaignBudgetChangeHistory campaignBudgetChangeHistory = this.campaignBudgetChangeHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
//                this.campaignBudgetChangeHistoryRepository.save(campaignBudgetChangeHistory);
//
//                if(!isLoop) {
//                    break;
//                }
//            }
//        } else {
//            throw new CampaignCashException();
//        }
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

                // 예산
                Float availableAmount = 0.0F;
                Float totalReserveAmount = 0.0F;
                Float budgetAmount = (float) request.getBudgetAmount() - (float) campaignInfo.getBudgetAmount();
                Float budgetAmountVat =  (float)(budgetAmount * 1.1);

                // 예약금액 증감
                String fluctuation = "";
                if (budgetAmount > 0) {
                    fluctuation = "P";
                } else {
                    fluctuation = "M";
                }

                if (availableAmountTotal > budgetAmountVat) {
                    availableAmount = list.getAvailableAmount() - budgetAmountVat;
                    totalReserveAmount = list.getTotalReserveAmount() + budgetAmountVat;

                    // 예산 업데이트
                    CampaignFindUtils.findByIdOrElseThrow(request.getCampaignId(), this.campaignRepository)
                            .updateBudget(request);

                    // 지갑 업데이트
                    this.walletMasterRepository.updateWalletMaster(campaignInfo.getBusinessAccountId(), availableAmount, totalReserveAmount);

                    // 지갑예약(예산)변경내역
                    WalletDto.Request.SaveWalletReserveLog log = new WalletDto.Request.SaveWalletReserveLog();
                    log.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                    log.setAdAccountId(campaignInfo.getAdAccountId());
                    log.setCampaignId(request.getCampaignId());
                    log.setFluctuation(fluctuation);
                    log.setTotalReserveAmount(budgetAmountVat);
                    log.setReserveAmount(budgetAmount);
                    log.setReserveVatAmount((float) (budgetAmount * 0.1));
                    WalletReserveLog walletReserveLog = this.walletReserveLogMapper.toEntity(log, SecurityUtils.getLoginUserNo());
                    this.walletReserveLogRepository.save(walletReserveLog);

                    // 캠페인 예산 변경 히스토리
                    CampaignBudgetChangeHistoryDto.Request.Save history = new CampaignBudgetChangeHistoryDto.Request.Save();
                    history.setBusinessAccountId(campaignInfo.getBusinessAccountId());
                    history.setAdAccountId(campaignInfo.getAdAccountId());
                    history.setCampaignId(request.getCampaignId());
                    history.setChgAmount(budgetAmountVat);
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






//        List<WalletDto.Response.WalletCashTotal> list = this.walletCashTotalRepository.getWalletCashTotal(campaignAdAccountId.getAdAccountId());
//
//        // 이용가능금액 조회
//        Float availableAmountSum = 0.0F;
//        for (WalletDto.Response.WalletCashTotal m: list) {
//            availableAmountSum += m.getAvailableAmount();
//        }
//
//        // 이용가능 금액 보다 크다면
//        Float availableAmount = 0.0F;
//        Float reserveAmount = 0.0F;
//        Float budgetAmount = (float)request.getBudgetAmount() - (float)campaignAdAccountId.getBudgetAmount();
//        Float budgetAmountRemain = budgetAmount;
//        Boolean isLoop = true;
//        if(availableAmountSum > budgetAmount) {
//            for (WalletDto.Response.WalletCashTotal m: list) {
//                if(budgetAmount == 0) {
//                    break;
//                } else if(budgetAmount > 0) {
//                    // 하나의 캐시가 큰 경우
//                    if(m.getAvailableAmount() > abs(budgetAmount)) {
//                        availableAmount = m.getAvailableAmount() - budgetAmount;
//                        reserveAmount = m.getReserveAmount() + budgetAmount;
//                        isLoop = false;
//                    } else {
//                        budgetAmountRemain = budgetAmount - m.getAvailableAmount();
//                        if(budgetAmountRemain >= 0) {
//                            availableAmount = 0.0F;
//                            reserveAmount = m.getReserveAmount() + m.getAvailableAmount();
//                        }
//                        budgetAmount = budgetAmountRemain;
//                        isLoop = true;
//                    }
//                } else {
//                    if(m.getReserveAmount() > abs(budgetAmount)) {
//                        availableAmount = m.getAvailableAmount() - budgetAmount;
//                        reserveAmount = m.getReserveAmount() + budgetAmount;
//                        isLoop = false;
//                    } else {
//                        budgetAmountRemain = budgetAmount + m.getReserveAmount();
//                        if(budgetAmountRemain < 0) {
//                            reserveAmount = 0.0F;
//                            availableAmount = m.getReserveAmount();
//                        }
//                        budgetAmount = budgetAmountRemain;
//                        isLoop = true;
//                    }
//                }
//
//                this.walletCashTotalRepository.saveWalletCashReserve(campaignAdAccountId.getAdAccountId(), m.getCashId(), availableAmount, reserveAmount);
//
//                CampaignBudgetChangeHistoryDto.Request.Save history = new CampaignBudgetChangeHistoryDto.Request.Save();
//                history.setAdAccountId(campaignAdAccountId.getAdAccountId());
//                history.setCampaignId(request.getCampaignId());
//                history.setCashId(m.getCashId());
//                history.setChgAmount(budgetAmount);
//                history.setAvailableAmount(m.getAvailableAmount());
//                history.setAvailableChgAmount(availableAmount);
//                history.setReserveAmount(m.getReserveAmount());
//                history.setReserveChgAmount(reserveAmount);
//                CampaignBudgetChangeHistory campaignBudgetChangeHistory = this.campaignBudgetChangeHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
//                this.campaignBudgetChangeHistoryRepository.save(campaignBudgetChangeHistory);
//
//                if(!isLoop) {
//                    break;
//                }
//            }
//        } else {
//            throw new CampaignCashException();
//        }
//
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
