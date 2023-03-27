package com.adplatform.restApi.domain.advertiser.creative.service;

import com.adplatform.restApi.domain.advertiser.creative.dao.CreativeMediaCategoryRepository;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeMediaCategory;
import com.adplatform.restApi.domain.advertiser.creative.exception.CreativeNotFoundException;

public class CreativeMediaCategoryFindUtils {
    public static CreativeMediaCategory findByIdOrElseThrow(Integer id, CreativeMediaCategoryRepository repository) {
        return repository.findById(id).orElseThrow(CreativeNotFoundException::new);
    }
}
