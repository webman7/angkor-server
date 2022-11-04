package com.adplatform.restApi.global.config.environment;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

@Profile("local")
@Configuration
public class LocalEnvironmentConfig implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        FileUtils.deleteDirectory(new File("./files"));
    }
}
