package com.adplatform.restApi.domain.advertiser.campaign.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.service.AdAccountFindUtils;
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
import com.adplatform.restApi.domain.history.dao.campaign.CampaignBudgetChangeHistoryRepository;
import com.adplatform.restApi.domain.history.domain.CampaignBudgetChangeHistory;
import com.adplatform.restApi.domain.history.dto.campaign.CampaignBudgetChangeHistoryDto;
import com.adplatform.restApi.domain.history.dto.campaign.CampaignBudgetChangeHistoryMapper;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.domain.wallet.dao.walletcashtotal.WalletCashTotalRepository;
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
 * @author Seohyun Lee
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
    private final WalletCashTotalRepository walletCashTotalRepository;
    private final CampaignBudgetChangeHistoryRepository campaignBudgetChangeHistoryRepository;

    private final CampaignBudgetChangeHistoryMapper campaignBudgetChangeHistoryMapper;

    public void save(CampaignDto.Request.Save request) {
        AdTypeAndGoal adTypeAndGoal = this.findAdTypeAndGoal(
                request.getAdTypeAndGoal().getAdTypeName(),
                request.getAdTypeAndGoal().getAdGoalName());
        AdAccount adAccount = AdAccountFindUtils.findByIdOrElseThrow(request.getAdAccountId(), this.adAccountRepository);

        Campaign campaign = this.campaignRepository.save(this.campaignMapper.toEntity(request, adTypeAndGoal, adAccount));
        this.mapToAdGroupSavedEvent(request.getAdGroups(), campaign).forEach(this.eventPublisher::publishEvent);

        List<WalletDto.Response.WalletCashTotal> list = this.walletCashTotalRepository.getWalletCashTotal(request.getAdAccountId());

        // 이용가능금액 조회
        Long availableAmountSum = 0L;
        for (WalletDto.Response.WalletCashTotal m: list) {
            availableAmountSum += m.getAvailableAmount();
        }

        // 이용가능 금액 보다 크다면
        Long availableAmount = 0L;
        Long reserveAmount = 0L;
        Long budgetAmount = request.getBudgetAmount();
        Long budgetAmountRemain = budgetAmount;
        Boolean isLoop = true;
        if(availableAmountSum > budgetAmount) {
            for (WalletDto.Response.WalletCashTotal m: list) {
                // 하나의 캐시가 큰 경우
                if(m.getAvailableAmount() - budgetAmount > 0) {
                    availableAmount = m.getAvailableAmount() - budgetAmount;
                    reserveAmount = m.getReserveAmount() + budgetAmount;
                    isLoop = false;
                } else {
                    budgetAmountRemain = budgetAmount - m.getAvailableAmount();
                    if(budgetAmountRemain >= 0) {
                        availableAmount = 0L;
                        reserveAmount = m.getReserveAmount() + m.getAvailableAmount();
                    }
                    budgetAmount = budgetAmountRemain;
                    isLoop = true;
                }
                this.walletCashTotalRepository.saveWalletCashReserve(request.getAdAccountId(), m.getCashId(), availableAmount, reserveAmount);

                CampaignBudgetChangeHistoryDto.Request.Save history = new CampaignBudgetChangeHistoryDto.Request.Save();
                history.setAdAccountId(request.getAdAccountId());
                history.setCampaignId(campaign.getId());
                history.setCashId(m.getCashId());
                history.setChgAmount(budgetAmount);
                history.setAvailableAmount(m.getAvailableAmount());
                history.setAvailableChgAmount(availableAmount);
                history.setReserveAmount(m.getReserveAmount());
                history.setReserveChgAmount(reserveAmount);
                CampaignBudgetChangeHistory campaignBudgetChangeHistory = this.campaignBudgetChangeHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
                this.campaignBudgetChangeHistoryRepository.save(campaignBudgetChangeHistory);

                if(!isLoop) {
                    break;
                }
            }
        } else {
            throw new CampaignCashException();
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

        CampaignDto.Response.CampaignByAdAccountId campaignAdAccountId = this.campaignRepository.getCampaignByAdAccountId(request.getCampaignId());

        List<WalletDto.Response.WalletCashTotal> list = this.walletCashTotalRepository.getWalletCashTotal(campaignAdAccountId.getAdAccountId());

        // 이용가능금액 조회
        Long availableAmountSum = 0L;
        for (WalletDto.Response.WalletCashTotal m: list) {
            availableAmountSum += m.getAvailableAmount();
        }

        // 이용가능 금액 보다 크다면
        Long availableAmount = 0L;
        Long reserveAmount = 0L;
        Long budgetAmount = request.getBudgetAmount() - campaignAdAccountId.getBudgetAmount();
        Long budgetAmountRemain = budgetAmount;
        Boolean isLoop = true;
        if(availableAmountSum > budgetAmount) {
            for (WalletDto.Response.WalletCashTotal m: list) {
                if(budgetAmount == 0) {
                    break;
                } else if(budgetAmount > 0) {
                    // 하나의 캐시가 큰 경우
                    if(m.getAvailableAmount() > abs(budgetAmount)) {
                        availableAmount = m.getAvailableAmount() - budgetAmount;
                        reserveAmount = m.getReserveAmount() + budgetAmount;
                        isLoop = false;
                    } else {
                        budgetAmountRemain = budgetAmount - m.getAvailableAmount();
                        if(budgetAmountRemain >= 0) {
                            availableAmount = 0L;
                            reserveAmount = m.getReserveAmount() + m.getAvailableAmount();
                        }
                        budgetAmount = budgetAmountRemain;
                        isLoop = true;
                    }
                } else {
                    if(m.getReserveAmount() > abs(budgetAmount)) {
                        availableAmount = m.getAvailableAmount() - budgetAmount;
                        reserveAmount = m.getReserveAmount() + budgetAmount;
                        isLoop = false;
                    } else {
                        budgetAmountRemain = budgetAmount + m.getReserveAmount();
                        if(budgetAmountRemain < 0) {
                            reserveAmount = 0L;
                            availableAmount = m.getReserveAmount();
                        }
                        budgetAmount = budgetAmountRemain;
                        isLoop = true;
                    }
                }

                this.walletCashTotalRepository.saveWalletCashReserve(campaignAdAccountId.getAdAccountId(), m.getCashId(), availableAmount, reserveAmount);

                CampaignBudgetChangeHistoryDto.Request.Save history = new CampaignBudgetChangeHistoryDto.Request.Save();
                history.setAdAccountId(campaignAdAccountId.getAdAccountId());
                history.setCampaignId(request.getCampaignId());
                history.setCashId(m.getCashId());
                history.setChgAmount(budgetAmount);
                history.setAvailableAmount(m.getAvailableAmount());
                history.setAvailableChgAmount(availableAmount);
                history.setReserveAmount(m.getReserveAmount());
                history.setReserveChgAmount(reserveAmount);
                CampaignBudgetChangeHistory campaignBudgetChangeHistory = this.campaignBudgetChangeHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
                this.campaignBudgetChangeHistoryRepository.save(campaignBudgetChangeHistory);

                if(!isLoop) {
                    break;
                }
            }
        } else {
            throw new CampaignCashException();
        }

        CampaignFindUtils.findByIdOrElseThrow(request.getCampaignId(), this.campaignRepository)
                .update(request);
    }

    public void delete(Integer id) {
        CampaignFindUtils.findByIdOrElseThrow(id, this.campaignRepository).delete();
    }

    public void changeConfig(Integer id, Campaign.Config config) {
        Campaign campaign = CampaignFindUtils.findByIdOrElseThrow(id, this.campaignRepository);
        if (config == Campaign.Config.ON) {
            campaign.changeConfigOn();
            // 기간 체크하여 Status 변경
            if(campaign.getSystemConfig().equals(Campaign.SystemConfig.ON)) {
                Calendar calendar = new GregorianCalendar();
                SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

                Integer chkDate = Integer.parseInt(SDF.format(calendar.getTime()));

                if (campaign.getStartDate() < chkDate) {
                    campaign.changeStatusReady();
                }
                else if( campaign.getStartDate() >= chkDate && campaign.getEndDate() <= chkDate) {
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
}
