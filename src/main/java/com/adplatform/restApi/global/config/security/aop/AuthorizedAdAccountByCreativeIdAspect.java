package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.creative.service.CreativeFindUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.adplatform.restApi.global.config.security.aop.AdAccountUserValidationUtil.validateAdAccountUser;

/**
 * @author Seohyun Lee
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCreativeId AuthorizedAdAccountByCreativeId
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
@Aspect
public class AuthorizedAdAccountByCreativeIdAspect {
    private final CreativeRepository creativeRepository;
    private final AdAccountUserRepository adAccountUserRepository;

    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCreativeId) && args(creativeId, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, Integer creativeId) throws Throwable {
        Integer adAccountId = CreativeFindUtils.findByIdOrElseThrow(creativeId, this.creativeRepository)
                .getAdGroup()
                .getCampaign()
                .getAdAccount()
                .getId();
        validateAdAccountUser(adAccountId, this.adAccountUserRepository);
        return joinPoint.proceed();
    }
}
