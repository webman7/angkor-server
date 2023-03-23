package com.adplatform.restApi.domain.media.dao.category;

import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryQuerydslRepository {

    Page<CategoryDto.Response.Search> search(Pageable pageable);
}
