package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.adaccount.exception.AdAccountUserNotFoundException;
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
 * 광고 계정(AdAccount) 권한 인가를 위한 로직 클래스.
 * <p>
 * {@link AuthorizedAdAccount} Annotation과 함께 사용되며, 광고 계정 권한 인가에 대한 실제 로직이 담겨있는 Aspect 클래스이다.
 *
 * @author Seohyun Lee
 * @since 1.0
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount AuthorizedAdAccount
 * @see com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter AdAccountIdGetter
 * @see com.adplatform.restApi.domain.campaign.dto.CampaignIdGetter CampaignIdGetter
 * @see com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupIdGetter AdGroupIdGetter
 * @see com.adplatform.restApi.domain.creative.dto.CreativeIdGetter CreativeIdGetter
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
        this.validateAdAccountUser(adAccountId);
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
        this.validateAdAccountUser(adAccountId);
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
        this.validateAdAccountUser(adAccountId);
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
        this.validateAdAccountUser(adAccountId);
        return joinPoint.proceed();
    }

    /**
     * 광고 계정 아이디와, 유저 아이디를 통해 해당 광고 계정에 접근할 수 있는 권한이 있는지 검증한다.
     * <p>
     * 해당 광고 계정에 접근하기 위한 권한이 있기 위해서는 {@link com.adplatform.restApi.domain.adaccount.domain.AdAccountUser AdAccountUser}가 존재해야하며,
     * {@link  com.adplatform.restApi.domain.adaccount.domain.AdAccountUser.RequestStatus requestStatus}가
     * {@link com.adplatform.restApi.domain.adaccount.domain.AdAccountUser.RequestStatus#Y Y}이어야 한다.
     * <p>
     * 만약 {@link com.adplatform.restApi.domain.adaccount.domain.AdAccountUser AdAccountUser}가 {@code null} 이라면
     * {@link AdAccountUserNotFoundException} 엑셉션이 발생된다.
     * <p>
     * 만약 {@link com.adplatform.restApi.domain.adaccount.domain.AdAccountUser.RequestStatus RequestStatus}가
     * {@link com.adplatform.restApi.domain.adaccount.domain.AdAccountUser.RequestStatus#Y Y}
     * 가 아니라면 {@link com.adplatform.restApi.domain.adaccount.exception.AdAccountUserAuthorizationException} 엑셉션이 발생된다.
     *
     * @param adAccountId
     * @exception AdAccountUserNotFoundException 해당 광고 계정에 접근 권한이 없습니다.
     * @exception com.adplatform.restApi.domain.adaccount.exception.AdAccountUserAuthorizationException 해당 광고 계정에 접근 권한이 없습니다.
     */
    private void validateAdAccountUser(Integer adAccountId) {
        AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(
                adAccountId,
                SecurityUtils.getLoginUserId(),
                this.adAccountUserRepository)
                .validateRequestStatus();
    }
}
