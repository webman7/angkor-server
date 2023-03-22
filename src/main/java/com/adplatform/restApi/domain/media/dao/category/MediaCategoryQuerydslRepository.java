package com.adplatform.restApi.domain.media.dao.category;

import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.domain.MediaCategory;
import com.adplatform.restApi.domain.media.dto.category.MediaCategoryDto;

import java.util.List;

public interface MediaCategoryQuerydslRepository {

    List<Category> findByMediaIdCategory(Integer id);
}
