package com.adplatform.restApi.domain.report.dao.custom;

import com.adplatform.restApi.domain.report.domain.ReportCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCustomRepository extends JpaRepository<ReportCustom, Integer>, ReportCustomQuerydslRepository {
}
