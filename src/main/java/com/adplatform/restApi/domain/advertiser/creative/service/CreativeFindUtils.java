package com.adplatform.restApi.domain.advertiser.creative.service;

import com.adplatform.restApi.domain.advertiser.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.exception.CreativeNotFoundException;

/**
 * @author junny
 * @since 1.0
 */
public class CreativeFindUtils {
    public static Creative findByIdOrElseThrow(Integer id, CreativeRepository repository) {
        return repository.findById(id).orElseThrow(CreativeNotFoundException::new);
    }
}
