package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.media.dao.category.CategoryRepository;
import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.exception.CategoryNotFoundException;

public class CategoryFindUtils {
    public static Category findByIdOrElseThrow(Integer id, CategoryRepository repository) {
        return repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
