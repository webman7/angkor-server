package com.adplatform.restApi.domain.adgroup.service;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.exception.AdGroupNotFoundException;

public class AdGroupFindUtils {
    public static AdGroup findById(Integer id, AdGroupRepository repository) {
        return repository.findById(id)
                .orElseThrow(AdGroupNotFoundException::new);
    }
}
