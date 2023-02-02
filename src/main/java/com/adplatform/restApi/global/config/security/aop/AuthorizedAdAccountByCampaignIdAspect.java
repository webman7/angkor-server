package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.advertiser.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.advertiser.campaign.service.CampaignFindUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.adplatform.restApi.global.config.security.aop.AdAccountUserValidationUtil.validateAdAccountUser;

/**
 * @author Seohyun Lee
 * @since 1.0
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCampaignId AuthorizedAdAccountByCampaignId
 */
@RequiredArgsConstructor
@Component
@Aspect
public class AuthorizedAdAccountByCampaignIdAspect {
    private final CampaignRepository campaignRepository;
    private final AdAccountUserRepository adAccountUserRepository;

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCampaignId) && args(campaignId, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, Integer campaignId) throws Throwable {
        Integer adAccountId = CampaignFindUtils.findByIdOrElseThrow(campaignId, this.campaignRepository)
                .getAdAccount()
                .getId();
        validateAdAccountUser(adAccountId, this.adAccountUserRepository);
        return joinPoint.proceed();
    }
}
