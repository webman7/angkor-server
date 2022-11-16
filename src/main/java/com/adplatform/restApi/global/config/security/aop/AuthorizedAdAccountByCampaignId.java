package com.adplatform.restApi.global.config.security.aop;

import java.lang.annotation.*;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AuthorizedAdAccountByCampaignId {
}
