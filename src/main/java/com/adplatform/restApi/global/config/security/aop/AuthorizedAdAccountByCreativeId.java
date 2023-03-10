package com.adplatform.restApi.global.config.security.aop;

import java.lang.annotation.*;

/**
 * @author junny
 * @since 1.0
 * @see com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccountByCreativeIdAspect AuthorizedAdAccountByCreativeIdAspect
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AuthorizedAdAccountByCreativeId {
}
