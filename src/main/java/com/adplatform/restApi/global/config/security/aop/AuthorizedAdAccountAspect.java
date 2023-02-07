package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupIdGetter;
import com.adplatform.restApi.domain.advertiser.adgroup.service.AdGroupFindUtils;
import com.adplatform.restApi.domain.advertiser.campaign.dao.campaign.CampaignRepository;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignIdGetter;
import com.adplatform.restApi.domain.advertiser.campaign.service.CampaignFindUtils;
import com.adplatform.restApi.domain.advertiser.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeIdGetter;
import com.adplatform.restApi.domain.advertiser.creative.service.CreativeFindUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.adplatform.restApi.global.config.security.aop.AdAccountUserValidationUtil.*;

/**
 * 광고 계정(AdAccount) 권한 인가를 위한 로직 클래스.
 * <p>
 * {@link AuthorizedAdAccount} Annotation과 함께 사용되며, 광고 계정 권한 인가에 대한 실제 로직이 담겨있는 Aspect 클래스이다.
 *
 * @author Seohyun Lee
 * @since 1.0
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount AuthorizedAdAccount
 * @see AdAccountIdGetter AdAccountIdGetter
 * @see CampaignIdGetter CampaignIdGetter
 * @see AdGroupIdGetter AdGroupIdGetter
 * @see CreativeIdGetter CreativeIdGetter
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

    /**
     * {@link AuthorizedAdAccount}와 {@link AdAccountIdGetter}를 사용한 경우 해당 메서드가 실행된다.
     *
     * @param joinPoint
     * @param adAccountIdGetter
     */
    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(adAccountIdGetter, ..)")
    public Object validateAuthorizeAdAccount(ProceedingJoinPoint joinPoint, AdAccountIdGetter adAccountIdGetter) throws Throwable {
        Integer adAccountId = adAccountIdGetter.getAdAccountId();
        validateAdAccountUser(adAccountId, this.adAccountUserRepository);
        return joinPoint.proceed();
    }

    /**
     * {@link AuthorizedAdAccount}와 {@link CampaignIdGetter}를 사용한 경우 해당 메서드가 실행된다.
     *
     * @param joinPoint
     * @param campaignIdGetter
     */
    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(campaignIdGetter, ..)")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, CampaignIdGetter campaignIdGetter) throws Throwable {
        Integer adAccountId = CampaignFindUtils.findByIdOrElseThrow(campaignIdGetter.getCampaignId(), this.campaignRepository)
                .getAdAccount()
                .getId();
        validateAdAccountUser(adAccountId, this.adAccountUserRepository);
        return joinPoint.proceed();
    }

    /**
     * {@link AuthorizedAdAccount}와 {@link AdGroupIdGetter}를 사용한 경우 해당 메서드가 실행된다.
     *
     * @param joinPoint
     * @param adGroupIdGetter
     */
    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(adGroupIdGetter, ..))")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, AdGroupIdGetter adGroupIdGetter) throws Throwable {
        Integer adAccountId = AdGroupFindUtils.findByIdOrElseThrow(adGroupIdGetter.getAdGroupId(), this.adGroupRepository)
                .getCampaign()
                .getAdAccount()
                .getId();
        validateAdAccountUser(adAccountId, this.adAccountUserRepository);
        return joinPoint.proceed();
    }

    /**
     * {@link AuthorizedAdAccount}와 {@link CreativeIdGetter}를 사용한 경우 해당 메서드가 실행된다.
     *
     * @param joinPoint
     * @param creativeIdGetter
     */
    @Around("@annotation(com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount) && args(creativeIdGetter, ..))")
    public Object validateAuthorizedAdAccount(ProceedingJoinPoint joinPoint, CreativeIdGetter creativeIdGetter) throws Throwable {
        Integer adAccountId = CreativeFindUtils.findByIdOrElseThrow(creativeIdGetter.getCreativeId(), this.creativeRepository)
                .getAdGroup()
                .getCampaign()
                .getAdAccount()
                .getId();
        validateAdAccountUser(adAccountId, this.adAccountUserRepository);
        return joinPoint.proceed();
    }
}
