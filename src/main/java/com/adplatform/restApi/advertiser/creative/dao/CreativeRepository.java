package com.adplatform.restApi.advertiser.creative.dao;

import com.adplatform.restApi.advertiser.creative.domain.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CreativeRepository extends JpaRepository<Creative, Integer>, CreativeQuerydslRepository {
}
