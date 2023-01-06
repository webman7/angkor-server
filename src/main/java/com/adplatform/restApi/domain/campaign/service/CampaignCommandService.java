package com.adplatform.restApi.domain.campaign.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.service.AdAccountFindUtils;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupEventMapper;
import com.adplatform.restApi.domain.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.campaign.dao.typegoal.AdTypeAndGoalRepository;
import com.adplatform.restApi.domain.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.campaign.domain.AdTypeAndGoal;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.campaign.dto.CampaignMapper;
import com.adplatform.restApi.domain.campaign.exception.AdTypeAndGoalNotFoundException;
import com.adplatform.restApi.domain.campaign.exception.CampaignCashException;
import com.adplatform.restApi.domain.wallet.dao.walletcashtotal.WalletCashTotalRepository;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void save(CampaignDto.Request.Save request) {
        AdTypeAndGoal adTypeAndGoal = this.findAdTypeAndGoal(
                request.getAdTypeAndGoal().getAdTypeName(),
                request.getAdTypeAndGoal().getAdGoalName());
        AdAccount adAccount = AdAccountFindUtils.findByIdOrElseThrow(request.getAdAccountId(), this.adAccountRepository);

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
                if(!isLoop) {
                    break;
                }
            }
        } else {
            throw new CampaignCashException();
        }
        Campaign campaign = this.campaignRepository.save(this.campaignMapper.toEntity(request, adTypeAndGoal, adAccount));
        this.mapToAdGroupSavedEvent(request.getAdGroups(), campaign).forEach(this.eventPublisher::publishEvent);
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
        if (config == Campaign.Config.ON) campaign.changeConfigOn();
        else if (config == Campaign.Config.OFF) campaign.changeConfigOff();
    }
}
