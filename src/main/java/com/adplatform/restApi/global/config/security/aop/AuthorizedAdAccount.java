package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupIdGetter;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignIdGetter;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeIdGetter;

import java.lang.annotation.*;

/**
 * 광고 계정(AdAccount) 권한 인가를 위한 Annotation.
 * <p>
 * 광고 계정 권한 인가 처리를 위해 사용되는 Annotation이며, Rest API의 Request params를 DTO를 통해 받을 때 사용한다.
 * <p>
 * 해당 어노테이션을 적용하기 위해서는 {@link AdAccountIdGetter AdAccountIdGetter},
 * {@link CampaignIdGetter CampaignIdGetter},
 * {@link AdGroupIdGetter AdGroupIdGetter},
 * {@link CreativeIdGetter CreativeIdGetter} 중 하나와 함께 사용해야 한다.
 * 해당 getter를 이용하여 {@link AuthorizedAdAccountAspect}를 통해 AOP로 광고 계정 권한을 검증한다.
 * <p>
 * DTO 방식이 아닌 {@link org.springframework.web.bind.annotation.PathVariable PathVariable}를 사용할 때는, 해당 Annotation이 아닌,
 * {@link AuthorizedAdAccountByCampaignId}, {@link AuthorizedAdAccountByAdGroupId}, {@link AuthorizedAdAccountByCreativeId}
 * 를 사용한다.
 *
 * @author junny
 * @since 1.0
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountAspect AuthorizedAdAccountAspect
 * @see AdAccountIdGetter AdAccountIdGetter
 * @see CampaignIdGetter CampaignIdGetter
 * @see AdGroupIdGetter AdGroupIdGetter
 * @see CreativeIdGetter CreativeIdGetter
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AuthorizedAdAccount {
}
