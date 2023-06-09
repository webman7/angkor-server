package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.service.AdAccountFindUtils;
import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.advertiser.campaign.service.CampaignFindUtils;
import com.adplatform.restApi.domain.business.dao.user.BusinessAccountUserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.adplatform.restApi.global.config.security.aop.AdAccountUserValidationUtil.validateAdAccountUser;

/**
 * @author junny
 * @since 1.0
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCampaignId AuthorizedAdAccountByCampaignId
 */
@RequiredArgsConstructor
@Component
@Aspect
public class AuthorizedAdAccountByCampaignIdAspect {
    private final AdAccountRepository adAccountRepository;
    private final CampaignRepository campaignRepository;
    private final AdAccountUserRepository adAccountUserRepository;
    private final BusinessAccountUserRepository businessAccountUserRepository;

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCampaignId) && args(campaignId, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, Integer campaignId) throws Throwable {
        Integer adAccountId = CampaignFindUtils.findByIdOrElseThrow(campaignId, this.campaignRepository)
                .getAdAccount()
                .getId();
        Integer businessAccountId = AdAccountFindUtils.findByIdOrElseThrow(adAccountId, this.adAccountRepository).getBusinessAccount().getId();
        validateAdAccountUser(businessAccountId, adAccountId, this.businessAccountUserRepository, this.adAccountUserRepository);
        return joinPoint.proceed();
    }
}
