package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupMediaDto;

import java.util.List;

public interface AdGroupMediaQuerydslRepository {

    List<AdGroupMediaDto.Response.Default> toAdGroupMedia(Integer adGroupId);
}
