package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.service.AdAccountUserQueryUtils;
import com.adplatform.restApi.domain.campaign.dao.CampaignRepository;
import com.adplatform.restApi.domain.campaign.service.CampaignFindUtils;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
@Aspect
public class AuthorizedAdAccountByCampaignIdAspect {
    private final CampaignRepository campaignRepository;
    private final AdAccountUserRepository adAccountUserRepository;

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCampaignId) && args(campaignId, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, Integer campaignId) throws Throwable {
        Integer adAccountId = CampaignFindUtils.findById(campaignId, this.campaignRepository)
                .getAdAccount()
                .getId();
        AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(
                adAccountId,
                SecurityUtils.getLoginUserId(),
                this.adAccountUserRepository);
        return joinPoint.proceed();
    }
}
