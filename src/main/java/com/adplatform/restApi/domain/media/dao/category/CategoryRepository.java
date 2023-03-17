package com.adplatform.restApi.domain.media.dao.category;

import com.adplatform.restApi.domain.media.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryQuerydslRepository {
}
