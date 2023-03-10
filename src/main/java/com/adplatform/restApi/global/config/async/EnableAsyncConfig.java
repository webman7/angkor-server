package com.adplatform.restApi.global.config.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *  Spring <i>Asynchronous</i> enable environment class.
 *
 * @author junny
 * @since 1.0
 * @see org.springframework.scheduling.annotation.Async Async
 * @see com.adplatform.restApi.infra.mail.event.EmailEventHandler EmailEventHandler
 */
@EnableAsync
@Configuration
public class EnableAsyncConfig {
}
