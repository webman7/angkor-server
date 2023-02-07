package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.service.AdGroupFindUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.adplatform.restApi.global.config.security.aop.AdAccountUserValidationUtil.*;

/**
 * @author Seohyun Lee
 * @since 1.0
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByAdGroupId AuthorizedAdAccountByAdGroupId
 */
@RequiredArgsConstructor
@Component
@Aspect
public class AuthorizedAdAccountByAdGroupIdAspect {
    private final AdGroupRepository adGroupRepository;
    private final AdAccountUserRepository adAccountUserRepository;

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByAdGroupId) && args(adGroupId, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, Integer adGroupId) throws Throwable {
        Integer adAccountId = AdGroupFindUtils.findByIdOrElseThrow(adGroupId, this.adGroupRepository)
                .getCampaign()
                .getAdAccount()
                .getId();
        validateAdAccountUser(adAccountId, this.adAccountUserRepository);
        return joinPoint.proceed();
    }
}
