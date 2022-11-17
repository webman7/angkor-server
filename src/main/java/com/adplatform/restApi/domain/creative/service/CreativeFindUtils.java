package com.adplatform.restApi.domain.creative.service;

import com.adplatform.restApi.domain.creative.dao.CreativeRepository;
import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.exception.CreativeNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CreativeFindUtils {
    public static Creative findByIdOrElseThrow(Integer id, CreativeRepository repository) {
        return repository.findById(id).orElseThrow(CreativeNotFoundException::new);
    }
}
