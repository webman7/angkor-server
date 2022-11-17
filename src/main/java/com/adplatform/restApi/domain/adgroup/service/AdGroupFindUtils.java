package com.adplatform.restApi.domain.adgroup.service;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.exception.AdGroupNotFoundException;

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
