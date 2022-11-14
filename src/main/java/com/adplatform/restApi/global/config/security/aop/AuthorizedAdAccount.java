package com.adplatform.restApi.global.config.security.aop;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AuthorizedAdAccount {
}
