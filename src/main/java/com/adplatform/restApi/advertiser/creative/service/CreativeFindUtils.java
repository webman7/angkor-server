package com.adplatform.restApi.advertiser.creative.service;

import com.adplatform.restApi.advertiser.creative.dao.CreativeRepository;
import com.adplatform.restApi.advertiser.creative.domain.Creative;
import com.adplatform.restApi.advertiser.creative.exception.CreativeNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CreativeFindUtils {
    public static Creative findByIdOrElseThrow(Integer id, CreativeRepository repository) {
        return repository.findById(id).orElseThrow(CreativeNotFoundException::new);
    }
}
