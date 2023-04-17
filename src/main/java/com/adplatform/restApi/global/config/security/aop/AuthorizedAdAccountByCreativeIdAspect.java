package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.service.AdAccountFindUtils;
import com.adplatform.restApi.domain.advertiser.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.advertiser.creative.service.CreativeFindUtils;
import com.adplatform.restApi.domain.business.dao.user.BusinessAccountUserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.adplatform.restApi.global.config.security.aop.AdAccountUserValidationUtil.validateAdAccountUser;

/**
 * @author junny
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCreativeId AuthorizedAdAccountByCreativeId
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
@Aspect
public class AuthorizedAdAccountByCreativeIdAspect {
    private final AdAccountRepository adAccountRepository;
    private final CreativeRepository creativeRepository;
    private final AdAccountUserRepository adAccountUserRepository;
    private final BusinessAccountUserRepository businessAccountUserRepository;

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCreativeId) && args(creativeId, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, Integer creativeId) throws Throwable {
        Integer adAccountId = CreativeFindUtils.findByIdOrElseThrow(creativeId, this.creativeRepository)
                .getAdGroup()
                .getCampaign()
                .getAdAccount()
                .getId();
        Integer businessAccountId = AdAccountFindUtils.findByIdOrElseThrow(adAccountId, this.adAccountRepository).getBusinessAccount().getId();
        validateAdAccountUser(businessAccountId, adAccountId, this.businessAccountUserRepository, this.adAccountUserRepository);
        return joinPoint.proceed();
    }
}
