package com.adplatform.restApi.advertiser.report.dao.custom;

import com.adplatform.restApi.advertiser.report.domain.ReportCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCustomRepository extends JpaRepository<ReportCustom, Integer>, ReportCustomQuerydslRepository {
}
