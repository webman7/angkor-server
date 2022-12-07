package com.adplatform.restApi.global.config.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(
        basePackages = {"com.adplatform.restApi.domain.**.dao.**.mapper"},
        annotationClass = Mapper.class
)
@Configuration
public class MyBatisConfig {
}
