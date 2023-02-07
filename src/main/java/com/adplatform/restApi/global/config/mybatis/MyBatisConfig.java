package com.adplatform.restApi.global.config.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(
        basePackages = {"com.adplatform.restApi.**.dao.**.mapper", "com.adplatform.restApi.domain.advertiser.**.dao.**.mapper", "com.adplatform.restApi.domain.agency.**.dao.**.mapper", "com.adplatform.restApi.batch.**.dao.**.mapper", "com.adplatform.restApi.admin.**.dao.**.mapper"},
        annotationClass = Mapper.class
)
@Configuration
public class MyBatisConfig {
}
