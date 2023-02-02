package com.adplatform.restApi.advertiser.adgroup.dao.adgroup;

import com.adplatform.restApi.advertiser.adgroup.domain.AdGroupMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdGroupMediaRepository  extends JpaRepository<AdGroupMedia, Integer>, AdGroupMediaQuerydslRepository {
}
