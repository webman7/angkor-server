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
import com.adplatform.restApi.domain.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.creative.dto.CreativeIdGetter;
import com.adplatform.restApi.domain.creative.service.CreativeFindUtils;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class AuthorizedAdAccountAspect {
    private final AdAccountUserRepository adAccountUserRepository;
    private final CampaignRepository campaignRepository;
    private final AdGroupRepository adGroupRepository;
    private final CreativeRepository creativeRepository;

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(adAccountIdGetter, ..)")
    public Object validateAuthorizeAdAccount(ProceedingJoinPoint joinPoint, AdAccountIdGetter adAccountIdGetter) throws Throwable {
        Integer adAccountId = adAccountIdGetter.getAdAccountId();
        this.validateAdAccountUser(adAccountId);
        return joinPoint.proceed();
    }

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(campaignIdGetter, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, CampaignIdGetter campaignIdGetter) throws Throwable {
        Integer adAccountId = CampaignFindUtils.findByIdOrElseThrow(campaignIdGetter.getCampaignId(), this.campaignRepository)
                .getAdAccount()
                .getId();
        this.validateAdAccountUser(adAccountId);
        return joinPoint.proceed();
    }

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(adGroupIdGetter, ..))")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, AdGroupIdGetter adGroupIdGetter) throws Throwable {
        Integer adAccountId = AdGroupFindUtils.findByIdOrElseThrow(adGroupIdGetter.getAdGroupId(), this.adGroupRepository)
                .getCampaign()
                .getAdAccount()
                .getId();
        this.validateAdAccountUser(adAccountId);
        return joinPoint.proceed();
    }

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(creativeIdGetter, ..))")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, CreativeIdGetter creativeIdGetter) throws Throwable {
        Integer adAccountId = CreativeFindUtils.findByIdOrElseThrow(creativeIdGetter.getCreativeId(), this.creativeRepository)
                .getAdGroup()
                .getCampaign()
                .getAdAccount()
                .getId();
        this.validateAdAccountUser(adAccountId);
        return joinPoint.proceed();
    }

    private void validateAdAccountUser(Integer adAccountId) {
        AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(
                adAccountId,
                SecurityUtils.getLoginUserId(),
                this.adAccountUserRepository);
    }
}
