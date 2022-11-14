package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.adaccount.service.AdAccountUserQueryUtils;
import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupIdGetter;
import com.adplatform.restApi.domain.adgroup.service.AdGroupFindUtils;
import com.adplatform.restApi.domain.campaign.dao.CampaignRepository;
import com.adplatform.restApi.domain.campaign.dto.CampaignIdGetter;
import com.adplatform.restApi.domain.campaign.service.CampaignFindUtils;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class AuthorizedAdAccountAspect {
    private final AdAccountUserRepository adAccountUserRepository;
    private final CampaignRepository campaignRepository;
    private final AdGroupRepository adGroupRepository;

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(adAccountIdGetter, ..)")
    public Object validateAuthorizeAdAccount(ProceedingJoinPoint joinPoint, AdAccountIdGetter adAccountIdGetter) throws Throwable {
        log.info("adAccountId: {}", adAccountIdGetter.getAdAccountId());
        AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(
                adAccountIdGetter.getAdAccountId(),
                SecurityUtils.getLoginUserId(),
                this.adAccountUserRepository);
        return joinPoint.proceed();
    }

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(campaignIdGetter, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, CampaignIdGetter campaignIdGetter) throws Throwable {
        Integer adAccountId = CampaignFindUtils.findById(campaignIdGetter.getCampaignId(), this.campaignRepository)
                .getAdAccount()
                .getId();
        AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(
                adAccountId,
                SecurityUtils.getLoginUserId(),
                this.adAccountUserRepository);
        return joinPoint.proceed();
    }

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(adGroupIdGetter, ..))")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, AdGroupIdGetter adGroupIdGetter) throws Throwable {
        Integer adAccountId = AdGroupFindUtils.findById(adGroupIdGetter.getAdGroupId(), this.adGroupRepository)
                .getCampaign()
                .getAdAccount()
                .getId();
        AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(
                adAccountId,
                SecurityUtils.getLoginUserId(),
                this.adAccountUserRepository);
        return joinPoint.proceed();
    }
}
