package com.adplatform.restApi.global.config.environment;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

/**
 * Local environment class.
 *
 * <p>로컬 환경에서 실행될 경우 스프링 컨테이너가 종료될 때 {@link LocalEnvironmentConfig#destroy()}가 실행된다.
 * @author Seohyun Lee
 * @since 1.0
 */
@Profile("local")
@Configuration
public class LocalEnvironmentConfig implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        FileUtils.deleteDirectory(new File("./files"));
    }
}
