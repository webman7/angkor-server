package com.adplatform.restApi.domain.media.dao.category;

import com.adplatform.restApi.domain.media.domain.MediaCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaCategoryRepository extends JpaRepository<MediaCategory, Integer>, MediaCategoryQuerydslRepository {
}
