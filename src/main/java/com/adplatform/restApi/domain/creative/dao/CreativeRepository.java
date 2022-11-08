package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreativeRepository extends JpaRepository<Creative, Integer>, CreativeQuerydslRepository {
}
