package com.adplatform.restApi.domain.advertiser.creative.dao;

import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeMediaCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreativeMediaCategoryRepository extends JpaRepository<CreativeMediaCategory, Integer>, CreativeMediaCategoryQuerydslRepository {
}
