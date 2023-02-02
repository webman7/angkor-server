package com.adplatform.restApi.advertiser.adgroup.service;

import com.adplatform.restApi.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.advertiser.adgroup.exception.AdGroupNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdGroupFindUtils {
    public static AdGroup findByIdOrElseThrow(Integer id, AdGroupRepository repository) {
        return repository.findById(id)
                .orElseThrow(AdGroupNotFoundException::new);
    }
}
